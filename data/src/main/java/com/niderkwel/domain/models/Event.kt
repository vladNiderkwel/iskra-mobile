package com.niderkwel.domain.models

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class Event(
    val id: Int = -1,
    val author: User,
    val title: String,
    val description: String,
    val members: List<User> = emptyList(),
    val startDate: LocalDateTime,
    val endDate: LocalDateTime
)

