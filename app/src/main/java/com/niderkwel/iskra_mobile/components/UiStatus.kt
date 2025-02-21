package com.niderkwel.iskra_mobile.components

sealed class UiStatus {
    data object Loading : UiStatus()
    data object Success : UiStatus()
    data class Error(val message: String = "") : UiStatus()
}