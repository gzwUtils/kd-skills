package com.kd.skills.agent.review

import com.kd.skills.agent.skills.Skill

object ReviewPromptBuilder {
    fun systemPrompt(skills: List<Skill>): String {
        val skillsText = if (skills.isEmpty()) {
            "No external SKILL.md loaded. Use general Java/Spring best practices."
        } else {
            buildString {
                appendLine("You MUST follow the review guidelines below (SKILL.md).")
                for (skill in skills) {
                    appendLine()
                    appendLine("## Skill: ${skill.name}")
                    if (skill.description.isNotBlank()) appendLine("Description: ${skill.description}")
                    appendLine(skill.body)
                }
            }
        }

        return """
            You are a senior Java architect and strict code reviewer.
            Task: Review the provided changes. Focus on correctness, maintainability, security, performance, testing, API design, and exception handling.
            Be concrete and actionable. Prefer pointing to specific files/lines when possible.

            Output MUST follow this exact format:
            VERDICT: PASS|BLOCK
            SUMMARY:
            <one paragraph>
            ISSUES:
            - ERROR|<file>|<line or -1>|<title>|<suggestion>
            - WARN|<file>|<line or -1>|<title>|<suggestion>
            - INFO|<file>|<line or -1>|<title>|<suggestion>
            If no issues, output:
            ISSUES:
            - NONE

            $skillsText
        """.trimIndent()
    }

    fun userPrompt(changesText: String): String {
        return """
            Changes:
            $changesText
        """.trimIndent()
    }
}

