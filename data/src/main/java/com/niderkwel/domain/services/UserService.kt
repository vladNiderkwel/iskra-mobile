package com.niderkwel.domain.services

import com.niderkwel.domain.models.Level
import com.niderkwel.domain.models.LoginForm
import com.niderkwel.domain.models.RegistrationForm
import com.niderkwel.domain.models.User
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    @GET("/user/{id}")
    suspend fun get(@Path("id") id: Int): Response<User?>

    @GET("/level/{id}")
    suspend fun level(@Path("id") id: Int): Response<Level?>

    @GET("/level/")
    suspend fun ratings(): Response<List<Level>>

    @GET("/user/email/{email}")
    suspend fun check(@Path("email") email: String): Response<User?>

    @POST("/user/")
    suspend fun create(@Body body: RegistrationForm): Response<User?>

    @POST("/user/login")
    suspend fun login(@Body form: LoginForm): Response<User?>

    @PUT("/user/")
    suspend fun update(@Body body: RequestBody): Response<Boolean>

    @DELETE("/user/")
    suspend fun delete(@Path("id") id: Int): Response<Nothing>
}