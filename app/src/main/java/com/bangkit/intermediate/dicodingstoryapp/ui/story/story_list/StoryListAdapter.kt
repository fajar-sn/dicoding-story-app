package com.bangkit.intermediate.dicodingstoryapp.ui.story.story_list

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.intermediate.dicodingstoryapp.R
import com.bangkit.intermediate.dicodingstoryapp.data.remote.response.Story
import com.bangkit.intermediate.dicodingstoryapp.databinding.ItemRowStoryBinding
import com.bangkit.intermediate.dicodingstoryapp.ui.story.detail.StoryDetailActivity
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

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, StoryDetailActivity::class.java)
                intent.putExtra(StoryDetailActivity.EXTRA_STORY, story)

                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    Pair(binding.imageViewStory, "profile"),
                    Pair(binding.textViewStoryName, "name"),
                    Pair(binding.textViewStoryBody, "story")
                )

                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
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
