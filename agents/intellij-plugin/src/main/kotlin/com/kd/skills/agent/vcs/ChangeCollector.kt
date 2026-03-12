package com.kd.skills.agent.vcs

import com.intellij.openapi.vcs.changes.Change
import com.intellij.openapi.vcs.VcsException
import com.intellij.openapi.vcs.changes.ContentRevision
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

object ChangeCollector {
    fun collect(changes: Collection<Change>, maxChars: Int): String {
        val sb = StringBuilder()
        for (change in changes) {
            val path = change.afterRevision?.file?.path
                ?: change.beforeRevision?.file?.path
                ?: "<unknown>"

            val before = revisionContent(change.beforeRevision)
            val after = revisionContent(change.afterRevision) ?: safeReadFile(change.afterRevision?.file?.path)

            sb.appendLine("=== FILE: $path")
            sb.appendLine("--- BEFORE")
            sb.appendLine(before ?: "<no before content>")
            sb.appendLine("--- AFTER")
            sb.appendLine(after ?: "<no after content>")
            sb.appendLine()

            if (sb.length >= maxChars) {
                sb.appendLine("=== TRUNCATED: maxChars=$maxChars ===")
                break
            }
        }

        return if (sb.length > maxChars) sb.substring(0, maxChars) else sb.toString()
    }

    private fun revisionContent(revision: ContentRevision?): String? {
        if (revision == null) return null
        return try {
            revision.content
        } catch (_: VcsException) {
            null
        } catch (_: Throwable) {
            null
        }
    }

    private fun safeReadFile(pathString: String?): String? {
        if (pathString.isNullOrBlank()) return null
        return try {
            val path = Path.of(pathString)
            if (!Files.isRegularFile(path)) return null
            Files.readString(path, StandardCharsets.UTF_8)
        } catch (_: Exception) {
            null
        }
    }
}
