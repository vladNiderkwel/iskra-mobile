package com.niderkwel.iskra_mobile.components.screens.ratings

import com.niderkwel.domain.models.Level
import com.niderkwel.iskra_mobile.components.UiStatus

data class RatingState(
    val uiStatus: UiStatus = UiStatus.Success,
    val levels: List<Level> = emptyList()
)