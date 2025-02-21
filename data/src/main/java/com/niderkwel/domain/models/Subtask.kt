package com.niderkwel.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Subtask(
    val id: Int = -1,
    val question: String,
    val type: Int,
    val options: List<Option> = emptyList()
)