package com.niderkwel.domain.services

import com.niderkwel.domain.models.UserTask
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserTaskService {

    @POST("/user-task")
    suspend fun create(@Body userTask: UserTask) : Response<UserTask>

    @GET("/user-task/user/{id}")
    suspend fun allOfUser(@Path("id") id: Int) : Response<List<UserTask>>
}