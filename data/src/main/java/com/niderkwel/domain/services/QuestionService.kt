package com.niderkwel.domain.services

import com.niderkwel.domain.models.PageResponse
import com.niderkwel.domain.models.Question
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface QuestionService {

    @GET("/question/")
    suspend fun all(
        @Query("page") page: Int = 1,
        @Query("query") query: String = ""
    ): Response<PageResponse<List<Question>>>

    @GET("/question/{id}")
    suspend fun get(@Path("id") id: Int): Response<Question?>

    @GET("/question/user/{id}")
    suspend fun getByUser(@Path("id") id: Int): Response<List<Question>?>

    @POST("/question/")
    suspend fun create(@Body question: Question): Response<Int>

    @DELETE("/question/")
    suspend fun delete(@Path("id") id: Int): Response<Nothing>
}