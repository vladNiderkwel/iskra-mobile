package com.niderkwel.iskra_mobile.components.screens.login

import com.niderkwel.iskra_mobile.components.UiStatus

data class LoginState(
    val uiStatus: UiStatus = UiStatus.Success,
    val name: String = "",
    val email: String = "",
    val password: String = "",
)