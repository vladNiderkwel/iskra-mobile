package com.niderkwel.domain.services

import com.niderkwel.domain.models.Event
import com.niderkwel.domain.models.PageResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface EventService {

    @GET("/event/")
    suspend fun all(
        @Query("page") page: Int = 1,
        @Query("query") query: String = ""
    ): Response<PageResponse<List<Event>>>

    @GET("/event/{id}")
    suspend fun get(@Path("id") id: Int): Response<Event?>

    @GET("/event/toggle")
    suspend fun toggleAttendance(
        @Query("eventId") event: Int,
        @Query("userId") user: Int,
    ): Response<Boolean>

    @POST("/event/")
    suspend fun create(@Body event: Event): Response<Int>

    @PUT("/event/")
    suspend fun update(@Body body: RequestBody): Response<Int>

    @DELETE("/event/")
    suspend fun delete(@Path("id") id: Int): Response<Nothing>
}