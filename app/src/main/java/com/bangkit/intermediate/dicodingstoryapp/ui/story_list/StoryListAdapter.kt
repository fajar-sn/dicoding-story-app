package com.bangkit.intermediate.dicodingstoryapp.ui.story_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.intermediate.dicodingstoryapp.R
import com.bangkit.intermediate.dicodingstoryapp.data.remote.response.Story
import com.bangkit.intermediate.dicodingstoryapp.databinding.ItemRowStoryBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class StoryListAdapter : ListAdapter<Story, StoryListAdapter.StoryListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRowStoryBinding.inflate(layoutInflater, parent, false)
        return StoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryListViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
    }

    class StoryListViewHolder(private val binding: ItemRowStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            binding.textViewStoryName.text = story.name
            binding.textViewStoryBody.text = story.description
            val requestOptions = RequestOptions.placeholderOf(R.drawable.ic_loading)
                .error(R.drawable.ic_error)

            Glide.with(itemView.context)
                .load(story.photoUrl)
                .apply(requestOptions)
                .into(binding.imageViewStory)

            itemView.setOnClickListener {}
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Story, newItem: Story) =
                oldItem.description == newItem.description
        }
    }
}
