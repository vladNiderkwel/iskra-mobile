package com.niderkwel.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegistrationForm (
    var name: String,
    var email: String,
    var password: String,
)