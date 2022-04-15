package com.bangkit.intermediate.dicodingstoryapp.data.remote.request

data class RegisterRequest(val name: String, val email: String, val password: String)

data class LoginRequest(val email: String, val password: String)