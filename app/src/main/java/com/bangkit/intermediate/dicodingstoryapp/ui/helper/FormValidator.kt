package com.bangkit.intermediate.dicodingstoryapp.ui.helper

import android.util.Patterns

object FormValidator {
    fun validateEmail(email: String) = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email)
        .matches()

    fun validatePassword(password: String) = password.isNotEmpty() && password.length > 6
}