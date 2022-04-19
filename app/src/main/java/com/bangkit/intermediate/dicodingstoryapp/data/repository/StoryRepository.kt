package com.bangkit.intermediate.dicodingstoryapp.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.bangkit.intermediate.dicodingstoryapp.data.remote.retrofit.ApiService
import retrofit2.HttpException
import java.net.SocketException

class StoryRepository private constructor(private val apiService: ApiService) : BaseRepository() {
    fun getStories(token: String) = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.getStories("Bearer $token")
            val stories = response.story
            emit(Result.Success(stories))
        } catch (e: Exception) {
            if (e is SocketException) {
                Log.e("AuthRepository", "getStories: ${e.message}")
                emit(e.message?.let { Result.Error(it) })
            } else {
                Log.e("AuthRepository", "getStories: ${(e as HttpException).message}")
                emit(Result.Error(e.message()))
            }
        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null

        fun getInstance(apiService: ApiService) = instance ?: synchronized(this) {
            instance ?: StoryRepository(apiService)
        }.also { instance = it }
    }
}