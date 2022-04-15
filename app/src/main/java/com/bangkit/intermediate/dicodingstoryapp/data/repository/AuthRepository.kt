package com.bangkit.intermediate.dicodingstoryapp.data.repository

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.bangkit.intermediate.dicodingstoryapp.data.remote.request.RegisterRequest
import com.bangkit.intermediate.dicodingstoryapp.data.remote.response.RegisterResponse
import com.bangkit.intermediate.dicodingstoryapp.data.remote.retrofit.ApiService
import java.lang.Exception

class AuthRepository private constructor(private val apiService: ApiService) : BaseRepository() {
//    private val result = MediatorLiveData<Result<RegisterResponse>>()

    fun register(request: RegisterRequest) = liveData {
        emit(Result.Loading)
        var response = RegisterResponse(false, "")

        try {
            response = apiService.register(request.name, request.email, request.password)
        } catch (e: Exception) {
            Log.e("AuthRepository", "register: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }

        emit(Result.Success(response))
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(apiService: ApiService) : AuthRepository = instance ?: synchronized(this) {
            instance ?: AuthRepository(apiService)
        }.also { instance = it }
    }
}