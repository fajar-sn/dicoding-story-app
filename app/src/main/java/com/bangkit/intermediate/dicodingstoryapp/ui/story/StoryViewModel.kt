package com.bangkit.intermediate.dicodingstoryapp.ui.story

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.bangkit.intermediate.dicodingstoryapp.data.remote.request.AddStoryRequest
import com.bangkit.intermediate.dicodingstoryapp.data.repository.StoryRepository
import com.bangkit.intermediate.dicodingstoryapp.di.Injection
import kotlinx.coroutines.launch

open class StoryViewModel : ViewModel() {
    protected val token = MutableLiveData<String>()

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
}

class StoryListViewModel(private val repository: StoryRepository) : StoryViewModel() {
    fun getStories() = token.value?.let { repository.getStories(it).cachedIn(viewModelScope) }
}

class AddStoryViewModel(private val repository: StoryRepository) : StoryViewModel() {
    fun addNewStory(request: AddStoryRequest) =
        token.value?.let { repository.addNewStory(it, request) }
}