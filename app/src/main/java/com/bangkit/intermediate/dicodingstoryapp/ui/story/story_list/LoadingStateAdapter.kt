package com.bangkit.intermediate.dicodingstoryapp.ui.story.story_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.intermediate.dicodingstoryapp.databinding.ItemLoadingBinding

class LoadingStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState)  = holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoadingStateViewHolder {
        val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding, retry)
    }

    class LoadingStateViewHolder(private val binding: ItemLoadingBinding, retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.buttonRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error)
                binding.textViewErrorMessage.text = loadState.error.localizedMessage

            binding.progressBarStoryList.isVisible = loadState is LoadState.Loading
            binding.buttonRetry.isVisible = loadState is LoadState.Error
            binding.textViewErrorMessage.isVisible = loadState is LoadState.Error
        }
    }
}