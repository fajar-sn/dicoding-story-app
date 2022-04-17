package com.bangkit.intermediate.dicodingstoryapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.intermediate.dicodingstoryapp.data.local.UserPreferences
import com.bangkit.intermediate.dicodingstoryapp.data.remote.retrofit.ApiConfig
import com.bangkit.intermediate.dicodingstoryapp.data.repository.AuthRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

object Injection {
    fun provideInjection(context: Context): AuthRepository {
        val apiService = ApiConfig.getApiService()
        val preferences = UserPreferences.getInstance(context.dataStore)
        return AuthRepository.getInstance(apiService, preferences)
    }
}