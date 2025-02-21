package com.niderkwel.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Question(
    val id: Int = -1,
    val author: User,
    val question: String,
    val answer: String = "",
    val phase: Byte
)