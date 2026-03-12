package com.kd.skills.agent.vcs

import com.intellij.openapi.vcs.checkin.CheckinHandler
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory
import com.intellij.openapi.vcs.checkin.CheckinProjectPanel
import com.intellij.openapi.vcs.checkin.CommitContext

class KdSkillsCheckinHandlerFactory : CheckinHandlerFactory() {
    override fun createHandler(panel: CheckinProjectPanel, commitContext: CommitContext): CheckinHandler {
        return KdSkillsCheckinHandler(panel.project, panel)
    }
}

