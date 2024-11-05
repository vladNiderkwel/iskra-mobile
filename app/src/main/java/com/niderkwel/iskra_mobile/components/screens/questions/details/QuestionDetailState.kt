package com.niderkwel.iskra_mobile.components.screens.questions.details

import com.niderkwel.domain.models.Question
import com.niderkwel.iskra_mobile.components.UiStatus

data class QuestionDetailsState(
    val uiStatus: UiStatus = UiStatus.Success,
    val question: Question? = null
)