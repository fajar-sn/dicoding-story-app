package com.bangkit.intermediate.dicodingstoryapp.ui.story.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.intermediate.dicodingstoryapp.data.remote.response.Story
import com.bangkit.intermediate.dicodingstoryapp.databinding.ActivityStoryDetailBinding
import com.bumptech.glide.Glide

class StoryDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupData(binding)
    }

    private fun setupData(binding: ActivityStoryDetailBinding) {
        val story = intent.getParcelableExtra<Story>("Story") as Story
        binding.textViewNameStoryDetail.text = story.name
        binding.textViewDescriptionStoryDetail.text = story.description

        Glide.with(applicationContext)
            .load(story.photoUrl)
            .circleCrop()
            .into(binding.imageViewStoryDetail)
    }
}