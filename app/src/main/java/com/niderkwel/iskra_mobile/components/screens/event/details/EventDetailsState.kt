package com.niderkwel.iskra_mobile.components.screens.event.details

import com.niderkwel.domain.models.Event
import com.niderkwel.iskra_mobile.components.UiStatus

data class EventDetailsState(
    val userId: Int = -1,
    val uiStatus: UiStatus = UiStatus.Success,
    val toggle: UiStatus = UiStatus.Success,
    val event: Event? = null
)