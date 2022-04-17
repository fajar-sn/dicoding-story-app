package com.bangkit.intermediate.dicodingstoryapp.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val userKey = stringPreferencesKey("user_token")

    fun getUserToken() = dataStore.data.map { it[userKey] }

    suspend fun saveUserToken(token: String) = dataStore.edit { it[userKey] = token }

    companion object {
        @Volatile
        private var instance: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>) = instance ?: synchronized(this) {
            instance ?: UserPreferences(dataStore)
        }.also { instance = it }
    }
}