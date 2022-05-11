package com.bangkit.intermediate.dicodingstoryapp.data.repository

import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.bangkit.intermediate.dicodingstoryapp.data.remote.request.AddStoryRequest
import com.bangkit.intermediate.dicodingstoryapp.data.remote.retrofit.ApiService
import retrofit2.HttpException
import java.net.SocketException

class StoryRepository private constructor(private val apiService: ApiService) : BaseRepository() {
    fun getStories(token: String) = Pager(
        config = PagingConfig(pageSize = 2),
        pagingSourceFactory = { StoryPagingSource(apiService, token) }
    ).liveData

    fun addNewStory(token: String, request: AddStoryRequest) = liveData {
        emit(Result.Loading)

        try {
            val response =
                apiService.addNewStory("Bearer $token", request.imageMultipart, request.description)

            if (!response.error) emit(Result.Success(response.message))
            else emit(Result.Error(response.message))
        } catch (e: Exception) {
            if (e is SocketException) emit(e.message?.let { Result.Error(it) })
            else emit(Result.Error((e as HttpException).message()))
        }
    }

    fun getLatestStories(token: String) = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.getStories("Bearer $token")
            if (!response.error) emit(Result.Success(response.story))
            else emit(Result.Error(response.message))
        } catch (e: Exception) {
            if (e is SocketException) emit(e.message?.let { Result.Error(it) })
            else emit(Result.Error((e as HttpException).message()))
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