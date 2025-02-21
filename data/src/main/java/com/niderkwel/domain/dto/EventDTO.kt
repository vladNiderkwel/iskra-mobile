package com.niderkwel.domain.dto

import com.niderkwel.domain.models.User
import java.time.LocalDateTime

data class EventDTO(
    val author: User,
    val title: String,
    val description: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime
)