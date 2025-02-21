package com.niderkwel.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PageResponse<T>(
    val currentPage: Int,
    val totalPages: Int,
    val content: T
)