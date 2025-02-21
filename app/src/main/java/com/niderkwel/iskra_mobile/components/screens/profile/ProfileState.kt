package com.niderkwel.iskra_mobile.components.screens.profile

import com.niderkwel.domain.models.Level
import com.niderkwel.domain.models.User
import com.niderkwel.domain.models.UserTask
import com.niderkwel.iskra_mobile.components.UiStatus

data class ProfileState(
    val uiStatus: UiStatus = UiStatus.Success,
    val user: User? = null,
    val userTasks: List<UserTask> = emptyList(),
    val level: Level? = null
)