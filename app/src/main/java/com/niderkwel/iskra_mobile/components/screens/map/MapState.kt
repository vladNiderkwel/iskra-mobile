package com.niderkwel.iskra_mobile.components.screens.map

import com.niderkwel.domain.models.MapMark
import com.niderkwel.iskra_mobile.components.UiStatus

data class MapState(
    val uiStatus: UiStatus = UiStatus.Success,
    val marks: List<MapMark> = emptyList()
)