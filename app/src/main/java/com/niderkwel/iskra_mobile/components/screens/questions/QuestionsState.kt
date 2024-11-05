package com.niderkwel.iskra_mobile.components.screens.questions

import com.niderkwel.domain.models.Question
import com.niderkwel.iskra_mobile.components.UiStatus

data class QuestionsState(
    val uiStatus: UiStatus = UiStatus.Success,
    val questions: List<Question> = emptyList()
)