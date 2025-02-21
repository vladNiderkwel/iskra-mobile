package com.niderkwel.domain

import com.niderkwel.domain.services.EventService
import com.niderkwel.domain.services.MapMarkService
import com.niderkwel.domain.services.PostService
import com.niderkwel.domain.services.QuestionService
import com.niderkwel.domain.services.TaskService
import com.niderkwel.domain.services.UserService
import com.niderkwel.domain.services.UserTaskService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val API_URL = "https://iskra-api.onrender.com"

object API {
    private val okHttpClient = OkHttpClient.Builder()
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(API_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .add(LocalDateAsStringAdapter())
                    .build()
            ).withNullSerialization()
        )
        .build()

    val userService = retrofit.create(UserService::class.java)
    val questionService = retrofit.create(QuestionService::class.java)
    val eventService = retrofit.create(EventService::class.java)
    val taskService = retrofit.create(TaskService::class.java)
    val postService = retrofit.create(PostService::class.java)
    val mapMarkService = retrofit.create(MapMarkService::class.java)
    val userTaskService = retrofit.create(UserTaskService::class.java)
}