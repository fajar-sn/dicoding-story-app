package com.bangkit.intermediate.dicodingstoryapp.ui.auth

import androidx.lifecycle.ViewModel
import com.bangkit.intermediate.dicodingstoryapp.data.remote.request.LoginRequest
import com.bangkit.intermediate.dicodingstoryapp.data.repository.AuthRepository
import com.bangkit.intermediate.dicodingstoryapp.data.remote.request.RegisterRequest

class AuthViewModel private constructor(private val authRepository: AuthRepository) : ViewModel() {
    fun register(request: RegisterRequest) = authRepository.register(request)

    fun login(request: LoginRequest) = authRepository.login(request)

    companion object {
        @Volatile
        private var instance: AuthViewModel? = null

        fun getInstance(authRepository: AuthRepository) = instance ?: synchronized(this) {
            instance ?: AuthViewModel(authRepository)
        }.also { instance = it }
    }
}