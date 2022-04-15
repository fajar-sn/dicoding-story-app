package com.bangkit.intermediate.dicodingstoryapp.di

import android.content.Context
import com.bangkit.intermediate.dicodingstoryapp.data.repository.AuthRepository
import com.bangkit.intermediate.dicodingstoryapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideInjection(context: Context): AuthRepository {
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(apiService)
    }
}