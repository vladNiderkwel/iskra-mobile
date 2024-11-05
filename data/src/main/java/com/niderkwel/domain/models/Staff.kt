package com.niderkwel.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Staff(
    val id: Int = -1,
    val name: String,
    val email: String,
    val password: String,
    val role: Byte = 0,
    var isBlocked: Boolean = false
)