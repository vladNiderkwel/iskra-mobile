package com.niderkwel.domain.services

import com.niderkwel.domain.PageResponse
import com.niderkwel.domain.models.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {
    @GET("/post/")
    suspend fun all(
        @Query("page") page: Int = 1,
    ): Response<PageResponse<List<Post>>>

    @GET("/post/{id}")
    suspend fun get(
        @Path("id") id: Int
    ): Response<Post?>

    @GET("/post/views/{id}")
    suspend fun increaseViews(
        @Path("id") id: Int
    ): Response<Boolean>
}