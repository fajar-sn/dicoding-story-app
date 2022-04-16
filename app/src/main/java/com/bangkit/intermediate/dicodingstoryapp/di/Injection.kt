package com.bangkit.intermediate.dicodingstoryapp.di

import com.bangkit.intermediate.dicodingstoryapp.data.remote.retrofit.ApiConfig
import com.bangkit.intermediate.dicodingstoryapp.data.repository.AuthRepository

object Injection {
    fun provideInjection(): AuthRepository {
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(apiService)
    }
}