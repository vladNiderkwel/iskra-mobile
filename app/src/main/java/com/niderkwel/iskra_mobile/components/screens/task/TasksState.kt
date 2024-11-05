package com.niderkwel.iskra_mobile.components.screens.task

import com.niderkwel.domain.models.Task
import com.niderkwel.domain.models.UserTask
import com.niderkwel.iskra_mobile.components.UiStatus

data class TasksState(
    val uiStatus: UiStatus = UiStatus.Success,
    val userTasks: List<UserTask> = emptyList(),
    val tasks: List<Task> = emptyList()
)