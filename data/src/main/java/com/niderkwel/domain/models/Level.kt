package com.niderkwel.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Level(
    val id: Int = -1,
    val user: User,
    var current: Int = 1,
    var expToNext: Int
)
