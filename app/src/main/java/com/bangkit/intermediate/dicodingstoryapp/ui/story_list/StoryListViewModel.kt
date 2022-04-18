package com.bangkit.intermediate.dicodingstoryapp.ui.story_list

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.intermediate.dicodingstoryapp.data.repository.StoryRepository
import com.bangkit.intermediate.dicodingstoryapp.di.Injection
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StoryListViewModel(private val repository: StoryRepository) : ViewModel() {
    private val token = MutableLiveData<String>()

    fun getToken(context: Context) {
        val authRepository = Injection.provideAuthInjection(context)
        val tokenFlow = authRepository.getUserToken()
        viewModelScope.launch {
            tokenFlow.collect {
                if (it == null) return@collect
                token.value = it
            }
        }
    }

    fun getStories() = token.value?.let { repository.getStories(it) }
}