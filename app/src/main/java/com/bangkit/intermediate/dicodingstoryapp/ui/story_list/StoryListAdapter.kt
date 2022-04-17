package com.bangkit.intermediate.dicodingstoryapp.ui.story_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.intermediate.dicodingstoryapp.data.remote.response.Story
import com.bangkit.intermediate.dicodingstoryapp.databinding.ItemRowStoryBinding
import com.bumptech.glide.Glide

class StoryListAdapter(private val storyList: List<Story>) :
    RecyclerView.Adapter<StoryListAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRowStoryBinding.inflate(layoutInflater, parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val binding = holder.binding
        binding.textViewStoryName.text = storyList[position].name
        binding.textViewStoryBody.text = storyList[position].description

        Glide.with(holder.itemView.context)
            .load(storyList[position].photoUrl)
            .into(binding.imageViewStory)
    }

    override fun getItemCount() = storyList.size

    class ListViewHolder(var binding: ItemRowStoryBinding) : RecyclerView.ViewHolder(binding.root)
}