package com.niderkwel.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Task (
    val id: Int = -1,
    val title: String,
    val subtasks: List<Subtask>,
    var available: Boolean = true,
    val reward: Int
)