package com.bangkit.intermediate.dicodingstoryapp.ui.auth

import androidx.lifecycle.ViewModel
import com.bangkit.intermediate.dicodingstoryapp.data.repository.AuthRepository
import com.bangkit.intermediate.dicodingstoryapp.data.remote.request.RegisterRequest

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun register(request: RegisterRequest) = authRepository.register(request)
}