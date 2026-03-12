package com.kd.skills.agent.skills

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

object SkillLoader {
    fun loadSkills(skillsRoot: Path): List<Skill> {
        if (!Files.isDirectory(skillsRoot)) {
            return emptyList()
        }

        val skills = mutableListOf<Skill>()
        Files.list(skillsRoot).use { entries ->
            entries
                .filter { Files.isDirectory(it) }
                .forEach { dir ->
                    val skillFile = dir.resolve("SKILL.md")
                    if (!Files.isRegularFile(skillFile)) return@forEach

                    val content = Files.readString(skillFile, StandardCharsets.UTF_8)
                    val parsed = parseSkill(content)
                    skills.add(
                        Skill(
                            name = parsed.name.ifBlank { dir.fileName.toString() },
                            description = parsed.description,
                            body = parsed.body.trim(),
                        )
                    )
                }
        }

        return skills.sortedBy { it.name }
    }

    fun selectSkills(allSkills: List<Skill>, skillNamesCsv: String): List<Skill> {
        val names = skillNamesCsv
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .toSet()
        if (names.isEmpty()) return allSkills

        return allSkills.filter { names.contains(it.name) }
    }

    private data class Parsed(val name: String, val description: String, val body: String)

    private fun parseSkill(raw: String): Parsed {
        val trimmed = raw.trimStart()
        if (!trimmed.startsWith("---")) {
            return Parsed(name = "", description = "", body = raw)
        }

        val lines = trimmed.lines()
        if (lines.isEmpty()) return Parsed(name = "", description = "", body = raw)

        var endIndex = -1
        for (i in 1 until lines.size) {
            if (lines[i].trim() == "---") {
                endIndex = i
                break
            }
        }
        if (endIndex <= 0) {
            return Parsed(name = "", description = "", body = raw)
        }

        val frontmatter = lines.subList(1, endIndex)
        val body = lines.drop(endIndex + 1).joinToString("\n")

        var name = ""
        var description = ""
        for (line in frontmatter) {
            val cleaned = line.trim()
            when {
                cleaned.startsWith("name:") -> name = cleaned.removePrefix("name:").trim().trim('"')
                cleaned.startsWith("description:") -> description =
                    cleaned.removePrefix("description:").trim().trim('"')
            }
        }
        return Parsed(name = name, description = description, body = body)
    }
}

