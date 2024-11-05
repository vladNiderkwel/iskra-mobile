package com.niderkwel.iskra_mobile.components.screens.event

import com.niderkwel.domain.models.Event
import com.niderkwel.iskra_mobile.components.UiStatus

data class EventsState(
    val uiStatus: UiStatus = UiStatus.Success,
    val events: List<Event> = emptyList()
)