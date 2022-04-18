package com.bangkit.intermediate.dicodingstoryapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.intermediate.dicodingstoryapp.data.remote.request.LoginRequest
import com.bangkit.intermediate.dicodingstoryapp.data.remote.request.RegisterRequest
import com.bangkit.intermediate.dicodingstoryapp.data.repository.AuthRepository
import kotlinx.coroutines.launch

open class AuthViewModel : ViewModel()

class SplashScreenViewModel(private val repository: AuthRepository) : AuthViewModel() {
    fun getUserToken() = repository.getUserToken().asLiveData()
}

class RegisterViewModel(private val repository: AuthRepository) : AuthViewModel() {
    fun register(request: RegisterRequest) = repository.register(request)
}

open class SettingsViewModel(private val repository: AuthRepository) : AuthViewModel() {
    fun saveUserToken(token: String) = viewModelScope.launch { repository.saveUserToken(token) }
}

class LoginViewModel(private val repository: AuthRepository) : SettingsViewModel(repository) {
    fun login(request: LoginRequest) = repository.login(request)
}