package com.niderkwel.iskra_mobile.components.screens.event.create

import com.niderkwel.iskra_mobile.components.UiStatus
import java.time.LocalDateTime

data class CreateEventState(
    val uiStatus: UiStatus = UiStatus.Success,
    val title: String = "",
    val description: String = "",
    val start: LocalDateTime = LocalDateTime.now(),
    val end: LocalDateTime = LocalDateTime.now().plusDays(1)
)