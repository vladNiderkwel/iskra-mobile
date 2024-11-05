package com.niderkwel.iskra_mobile.components.screens.registration

import com.niderkwel.iskra_mobile.components.UiStatus

data class RegistrationState (
    var uiStatus: UiStatus = UiStatus.Success,
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var passwordConfirm: String = "",
)