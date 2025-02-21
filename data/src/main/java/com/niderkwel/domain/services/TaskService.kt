package com.niderkwel.domain.services

import com.niderkwel.domain.models.PageResponse
import com.niderkwel.domain.models.Task
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TaskService {
    @GET("/task/")
    suspend fun all(
        @Query("page") page: Int = 1,
        @Query("query") query: String = ""
    ): Response<PageResponse<List<Task>>>

    @GET("/task/{id}")
    suspend fun get(@Path("id") id: Int): Response<Task?>
}