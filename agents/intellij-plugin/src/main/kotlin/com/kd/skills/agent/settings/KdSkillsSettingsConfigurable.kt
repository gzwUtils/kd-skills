package com.kd.skills.agent.settings

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JPasswordField
import javax.swing.JSpinner
import javax.swing.JTextField
import javax.swing.SpinnerNumberModel
import javax.swing.JCheckBox

class KdSkillsSettingsConfigurable(private val project: Project) : Configurable {
    private val state = KdSkillsSettingsState.getInstance(project)

    private var panel: JPanel? = null

    private val enabled = JCheckBox("Enable KD Skills Agent")

    private val skillsRootPath = TextFieldWithBrowseButton()
    private val skillNamesCsv = JTextField()

    private val baseUrl = JTextField()
    private val apiKey = JPasswordField()
    private val model = JTextField()

    private val timeoutSeconds = JSpinner(SpinnerNumberModel(60, 5, 600, 5))
    private val maxChangesChars = JSpinner(SpinnerNumberModel(20_000, 1_000, 200_000, 1_000))

    override fun getDisplayName(): String = "KD Skills Agent"

    override fun createComponent(): JComponent {
        skillsRootPath.addBrowseFolderListener(
            "Select skills root",
            "Select the folder that contains your skill subfolders (each with SKILL.md).",
            project,
            FileChooserDescriptorFactory.createSingleFolderDescriptor()
        )

        panel = FormBuilder.createFormBuilder()
            .addComponent(enabled)
            .addSeparator()
            .addLabeledComponent(JBLabel("Skills root path"), skillsRootPath)
            .addLabeledComponent(JBLabel("Skill names (comma-separated, empty = all)"), skillNamesCsv)
            .addSeparator()
            .addLabeledComponent(JBLabel("OpenAI-compatible base URL"), baseUrl)
            .addLabeledComponent(JBLabel("API key"), apiKey)
            .addLabeledComponent(JBLabel("Model"), model)
            .addLabeledComponent(JBLabel("Timeout (sec)"), timeoutSeconds)
            .addSeparator()
            .addLabeledComponent(JBLabel("Max changes chars"), maxChangesChars)
            .panel

        reset()
        return panel as JPanel
    }

    override fun isModified(): Boolean {
        val currentApiKey = String(apiKey.password)
        return enabled.isSelected != state.enabled ||
            skillsRootPath.text != state.skillsRootPath ||
            skillNamesCsv.text != state.skillNamesCsv ||
            baseUrl.text != state.openAiBaseUrl ||
            currentApiKey != state.openAiApiKey ||
            model.text != state.openAiModel ||
            (timeoutSeconds.value as Int) != state.requestTimeoutSeconds ||
            (maxChangesChars.value as Int) != state.maxChangesChars
    }

    override fun apply() {
        state.enabled = enabled.isSelected
        state.skillsRootPath = skillsRootPath.text.trim()
        state.skillNamesCsv = skillNamesCsv.text.trim()
        state.openAiBaseUrl = baseUrl.text.trim()
        state.openAiApiKey = String(apiKey.password)
        state.openAiModel = model.text.trim()
        state.requestTimeoutSeconds = (timeoutSeconds.value as Int)
        state.maxChangesChars = (maxChangesChars.value as Int)
    }

    override fun reset() {
        enabled.isSelected = state.enabled

        skillsRootPath.text = state.skillsRootPath
        skillNamesCsv.text = state.skillNamesCsv

        baseUrl.text = state.openAiBaseUrl
        apiKey.text = state.openAiApiKey
        model.text = state.openAiModel

        timeoutSeconds.value = state.requestTimeoutSeconds
        maxChangesChars.value = state.maxChangesChars
    }

    override fun disposeUIResources() {
        panel = null
    }
}

