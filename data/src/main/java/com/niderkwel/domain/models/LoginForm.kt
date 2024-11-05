package com.niderkwel.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginForm(
    val email: String,
    val password: String,
)