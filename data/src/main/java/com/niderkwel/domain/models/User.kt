package com.niderkwel.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User (
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val photoUrl: String,
    val isBlocked: Boolean,
    val isDeleted: Boolean
)