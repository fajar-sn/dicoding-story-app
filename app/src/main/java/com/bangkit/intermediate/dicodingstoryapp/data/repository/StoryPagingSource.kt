package com.bangkit.intermediate.dicodingstoryapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bangkit.intermediate.dicodingstoryapp.data.remote.response.Story
import com.bangkit.intermediate.dicodingstoryapp.data.remote.retrofit.ApiService

class StoryPagingSource(private val apiService: ApiService, private val token: String) : PagingSource<Int, Story>() {
    override fun getRefreshKey(state: PagingState<Int, Story>) = state.anchorPosition?.let { anchorPosition ->
        val anchorPage = state.closestPageToPosition(anchorPosition)
        anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>) = try {
        val position = params.key ?: INITIAL_PAGE_INDEX
        val responseData = apiService.getStories("Bearer $token", position, params.loadSize)

        LoadResult.Page(
            data = responseData.story,
            prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
            nextKey = if (responseData.story.isNullOrEmpty()) null else position + 1
        )
    } catch (exception: Exception) {
        LoadResult.Error(exception)
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}