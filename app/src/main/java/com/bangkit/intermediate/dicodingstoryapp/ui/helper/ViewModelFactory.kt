package com.bangkit.intermediate.dicodingstoryapp.ui.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.intermediate.dicodingstoryapp.data.repository.AuthRepository
import com.bangkit.intermediate.dicodingstoryapp.data.repository.BaseRepository
import com.bangkit.intermediate.dicodingstoryapp.data.repository.StoryRepository
import com.bangkit.intermediate.dicodingstoryapp.di.Injection
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.LoginViewModel
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.RegisterViewModel
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.SettingsViewModel
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.SplashScreenViewModel
import com.bangkit.intermediate.dicodingstoryapp.ui.story.AddStoryViewModel
import com.bangkit.intermediate.dicodingstoryapp.ui.story.StoryListViewModel
import com.bangkit.intermediate.dicodingstoryapp.ui.story.StoryLocationViewModel

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
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java))
            return SettingsViewModel(repository as AuthRepository) as T
        if (modelClass.isAssignableFrom(StoryListViewModel::class.java))
            return StoryListViewModel(repository as StoryRepository) as T
        if (modelClass.isAssignableFrom(AddStoryViewModel::class.java))
            return AddStoryViewModel(repository as StoryRepository) as T
        if (modelClass.isAssignableFrom(StoryLocationViewModel::class.java))
            return StoryLocationViewModel(repository as StoryRepository) as T

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getAuthInstance(context: Context): ViewModelFactory {
            if (instance == null || instance!!.repository !is AuthRepository) {
                val repository = Injection.provideAuthInjection(context)
                instance = ViewModelFactory(repository)
            }

            return instance as ViewModelFactory
        }

        fun getStoryInstance(): ViewModelFactory {
            if (instance == null || instance!!.repository !is StoryRepository) {
                val repository = Injection.provideStoryInjection()
                instance = ViewModelFactory(repository)
            }

            return instance as ViewModelFactory
        }
    }
}