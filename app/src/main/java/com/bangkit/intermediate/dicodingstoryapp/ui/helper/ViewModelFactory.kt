package com.bangkit.intermediate.dicodingstoryapp.ui.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.intermediate.dicodingstoryapp.data.repository.AuthRepository
import com.bangkit.intermediate.dicodingstoryapp.data.repository.BaseRepository
import com.bangkit.intermediate.dicodingstoryapp.di.Injection
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.LoginViewModel
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.RegisterViewModel
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.SplashScreenViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val repository: BaseRepository?,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java))
            return SplashScreenViewModel(repository as AuthRepository) as T
        if (modelClass.isAssignableFrom(LoginViewModel::class.java))
            return LoginViewModel(repository as AuthRepository) as T
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java))
            return RegisterViewModel(repository as AuthRepository) as T

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            val repository = Injection.provideInjection(context)
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(repository)
            }.also { instance = it }
        }
    }
}