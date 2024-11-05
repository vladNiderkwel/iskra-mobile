package com.niderkwel.iskra_mobile.components.screens.task.details

import com.niderkwel.domain.models.Task
import com.niderkwel.domain.models.UserTask
import com.niderkwel.iskra_mobile.components.UiStatus

data class TaskDetailsState(
    val uiStatus: UiStatus = UiStatus.Success,
    val saving: UiStatus = UiStatus.Success,
    val task: Task? = null,
    val userTask: UserTask? = null
)