package com.niderkwel.domain

data class PageResponse<T>(
    val currentPage: Int,
    val totalPages: Int,
    val content: T
)