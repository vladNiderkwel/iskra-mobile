package com.niderkwel.domain.dto

import com.niderkwel.domain.models.User

data class QuestionDTO(
    val author: User,
    val question: String
)