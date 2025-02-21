package com.niderkwel.domain.services

import com.niderkwel.domain.models.MapMark
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MapMarkService {
    @GET("/map-mark/")
    suspend fun all(): Response<List<MapMark>>

    @GET("/map-mark/{id}")
    suspend fun get(@Path("id") id: Int): Response<MapMark?>
}