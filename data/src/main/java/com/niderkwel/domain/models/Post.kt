package com.niderkwel.domain.models

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class Post(
    val id: Int = -1,
    val title: String,
    val description: String,
    val body: String,
    val photoUrl: String = "post_placeholder",
    val publicationDate: LocalDateTime,
    val author: Staff,
    val views: Int = 0
)