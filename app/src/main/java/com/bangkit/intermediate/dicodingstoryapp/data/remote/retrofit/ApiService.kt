package com.bangkit.intermediate.dicodingstoryapp.data.remote.retrofit

import com.bangkit.intermediate.dicodingstoryapp.data.remote.response.LoginResponse
import com.bangkit.intermediate.dicodingstoryapp.data.remote.response.RegisterResponse
import com.bangkit.intermediate.dicodingstoryapp.data.remote.response.StoryListResponse
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(@Header("Authorization") authorization: String): StoryListResponse
}