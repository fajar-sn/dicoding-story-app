package com.bangkit.intermediate.dicodingstoryapp.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.bangkit.intermediate.dicodingstoryapp.data.local.UserPreferences
import com.bangkit.intermediate.dicodingstoryapp.data.remote.request.LoginRequest
import com.bangkit.intermediate.dicodingstoryapp.data.remote.request.RegisterRequest
import com.bangkit.intermediate.dicodingstoryapp.data.remote.retrofit.ApiService
import retrofit2.HttpException

class AuthRepository private constructor(
    private val apiService: ApiService,
    private val preferences: UserPreferences,
) : BaseRepository() {
    fun register(request: RegisterRequest) = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.register(request.name, request.email, request.password)
            if (!response.error) emit(Result.Success(response))
            else emit(Result.Error(response.message))
        } catch (e: Exception) {
            Log.e("AuthRepository", "register: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(request: LoginRequest) = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.login(request.email, request.password)
            emit(Result.Success(response.loginResult))
        } catch (e: Exception) {
            Log.e("AuthRepository", "login: ${(e as HttpException).message}")
            emit(Result.Error(e.message()))
        }
    }

    fun getUserToken() = preferences.getUserToken()

    suspend fun saveUserToken(token: String) {
        preferences.saveUserToken(token)
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(apiService: ApiService, preferences: UserPreferences) =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService, preferences)
            }.also { instance = it }
    }
}