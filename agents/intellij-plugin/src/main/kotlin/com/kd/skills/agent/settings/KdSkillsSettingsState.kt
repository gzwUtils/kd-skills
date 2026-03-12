package com.kd.skills.agent.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

@Service(Service.Level.PROJECT)
@State(
    name = "KdSkillsAgentSettings",
    storages = [Storage("kd-skills-agent.xml")]
)
class KdSkillsSettingsState : PersistentStateComponent<KdSkillsSettingsState> {
    var enabled: Boolean = true

    var skillsRootPath: String = ""
    var skillNamesCsv: String = ""

    var openAiBaseUrl: String = "https://api.openai.com/v1"
    var openAiApiKey: String = ""
    var openAiModel: String = "gpt-4o-mini"

    var requestTimeoutSeconds: Int = 60
    var maxChangesChars: Int = 20_000

    override fun getState(): KdSkillsSettingsState = this

    override fun loadState(state: KdSkillsSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(project: Project): KdSkillsSettingsState = project.getService(KdSkillsSettingsState::class.java)
    }
}

