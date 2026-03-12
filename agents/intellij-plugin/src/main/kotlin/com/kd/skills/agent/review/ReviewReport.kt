package com.kd.skills.agent.review

data class ReviewIssue(
    val severity: String,
    val file: String,
    val line: Int?,
    val title: String,
    val suggestion: String,
)

data class ReviewReport(
    val verdict: Verdict,
    val summary: String,
    val issues: List<ReviewIssue>,
) {
    enum class Verdict { PASS, BLOCK }
}

object ReviewReportParser {
    fun parse(raw: String): ReviewReport {
        val lines = raw.lines().map { it.trimEnd() }

        val verdict = lines.firstOrNull { it.startsWith("VERDICT:") }
            ?.substringAfter("VERDICT:")
            ?.trim()
            ?.let { if (it.equals("BLOCK", ignoreCase = true)) ReviewReport.Verdict.BLOCK else ReviewReport.Verdict.PASS }
            ?: ReviewReport.Verdict.PASS

        val summary = buildString {
            var inSummary = false
            for (line in lines) {
                if (line.startsWith("SUMMARY:")) {
                    inSummary = true
                    val rest = line.substringAfter("SUMMARY:").trim()
                    if (rest.isNotEmpty()) appendLine(rest)
                    continue
                }
                if (line.startsWith("ISSUES:")) break
                if (inSummary) appendLine(line)
            }
        }.trim()

        val issues = mutableListOf<ReviewIssue>()
        var inIssues = false
        for (line in lines) {
            if (line.startsWith("ISSUES:")) {
                inIssues = true
                continue
            }
            if (!inIssues) continue
            val item = line.trim().removePrefix("-").trim()
            if (item.isEmpty()) continue
            if (item.equals("NONE", ignoreCase = true)) break

            val parts = item.split("|").map { it.trim() }
            if (parts.size < 5) continue
            val severity = parts[0]
            val file = parts[1]
            val lineNumber = parts[2].toIntOrNull()
            val title = parts[3]
            val suggestion = parts.drop(4).joinToString(" | ")
            issues.add(ReviewIssue(severity, file, lineNumber, title, suggestion))
        }

        return ReviewReport(verdict = verdict, summary = summary, issues = issues)
    }
}

