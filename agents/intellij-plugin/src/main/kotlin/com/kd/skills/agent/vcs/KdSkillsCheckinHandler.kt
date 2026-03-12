package com.kd.skills.agent.vcs

import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.checkin.CheckinHandler
import com.intellij.openapi.vcs.checkin.CheckinProjectPanel
import com.kd.skills.agent.llm.OpenAiCompatibleClient
import com.kd.skills.agent.review.ReviewPromptBuilder
import com.kd.skills.agent.review.ReviewReport
import com.kd.skills.agent.review.ReviewReportParser
import com.kd.skills.agent.settings.KdSkillsSettingsState
import com.kd.skills.agent.skills.SkillLoader
import java.nio.file.Path
import java.time.Duration

class KdSkillsCheckinHandler(
    private val project: Project,
    private val panel: CheckinProjectPanel,
) : CheckinHandler() {
    override fun beforeCheckin(): ReturnResult {
        val settings = KdSkillsSettingsState.getInstance(project)
        if (!settings.enabled) return ReturnResult.COMMIT

        val apiKey = settings.openAiApiKey.trim()
        if (apiKey.isBlank()) {
            return ReturnResult.COMMIT
        }

        val changes = panel.selectedChanges
        if (changes.isEmpty()) return ReturnResult.COMMIT

        val changesText = ChangeCollector.collect(changes, settings.maxChangesChars)

        val skills = runCatching {
            val skillsRoot = resolveSkillsRoot(settings.skillsRootPath)
            if (skillsRoot == null) emptyList()
            else SkillLoader.selectSkills(SkillLoader.loadSkills(skillsRoot), settings.skillNamesCsv)
        }.getOrDefault(emptyList())

        val systemPrompt = ReviewPromptBuilder.systemPrompt(skills)
        val userPrompt = ReviewPromptBuilder.userPrompt(changesText)

        var raw: String? = null
        var error: Throwable? = null

        ProgressManager.getInstance().runProcessWithProgressSynchronously(
            {
                try {
                    val client = OpenAiCompatibleClient(
                        baseUrl = settings.openAiBaseUrl,
                        apiKey = apiKey,
                        model = settings.openAiModel,
                        timeout = Duration.ofSeconds(settings.requestTimeoutSeconds.toLong()),
                    )
                    raw = client.chat(systemPrompt = systemPrompt, userPrompt = userPrompt)
                } catch (t: Throwable) {
                    error = t
                }
            },
            "KD Skills Agent: Reviewing changes",
            true,
            project
        )

        if (error != null) {
            val proceed = Messages.showYesNoDialog(
                project,
                "KD Skills Agent failed to review changes:\n\n${error?.message}\n\nCommit anyway?",
                "KD Skills Agent",
                "Commit",
                "Cancel",
                Messages.getWarningIcon()
            )
            return if (proceed == Messages.YES) ReturnResult.COMMIT else ReturnResult.CANCEL
        }

        val report = runCatching { ReviewReportParser.parse(raw.orEmpty()) }.getOrNull()
            ?: ReviewReport(ReviewReport.Verdict.PASS, summary = raw.orEmpty(), issues = emptyList())

        val message = formatReport(report)
        val proceed = if (report.verdict == ReviewReport.Verdict.BLOCK) {
            Messages.showYesNoDialog(
                project,
                message,
                "KD Skills Agent (BLOCK)",
                "Commit Anyway",
                "Cancel",
                Messages.getErrorIcon()
            )
        } else {
            Messages.showYesNoDialog(
                project,
                message,
                "KD Skills Agent",
                "Commit",
                "Cancel",
                Messages.getInformationIcon()
            )
        }

        return if (proceed == Messages.YES) ReturnResult.COMMIT else ReturnResult.CANCEL
    }

    private fun resolveSkillsRoot(raw: String): Path? {
        val trimmed = raw.trim()
        if (trimmed.isNotEmpty()) return runCatching { Path.of(trimmed) }.getOrNull()
        val env = System.getenv("KD_SKILLS_HOME")?.trim()
        if (!env.isNullOrEmpty()) return runCatching { Path.of(env) }.getOrNull()
        return null
    }

    private fun formatReport(report: ReviewReport): String {
        val sb = StringBuilder()
        sb.appendLine("VERDICT: ${report.verdict}")
        if (report.summary.isNotBlank()) {
            sb.appendLine()
            sb.appendLine("SUMMARY:")
            sb.appendLine(report.summary.trim())
        }
        if (report.issues.isNotEmpty()) {
            sb.appendLine()
            sb.appendLine("ISSUES:")
            for (issue in report.issues) {
                val line = issue.line ?: -1
                sb.appendLine("- ${issue.severity} | ${issue.file} | $line | ${issue.title} | ${issue.suggestion}")
            }
        }
        return sb.toString().trim()
    }
}

