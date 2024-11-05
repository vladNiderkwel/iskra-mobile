package com.niderkwel.iskra_mobile.components.screens.questions.create

import com.niderkwel.iskra_mobile.components.UiStatus

data class CreateQuestionState(
    val uiStatus: UiStatus = UiStatus.Success,
    val question: String = ""
)