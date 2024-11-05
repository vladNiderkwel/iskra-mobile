package com.niderkwel.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Option(
    val id: Int = -1,
    val text: String,
    val isAnswer: Boolean = false,
)