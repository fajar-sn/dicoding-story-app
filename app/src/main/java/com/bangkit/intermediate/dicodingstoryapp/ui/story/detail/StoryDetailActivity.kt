package com.bangkit.intermediate.dicodingstoryapp.ui.story.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.bangkit.intermediate.dicodingstoryapp.data.remote.response.Story
import com.bangkit.intermediate.dicodingstoryapp.databinding.ActivityStoryDetailBinding
import com.bangkit.intermediate.dicodingstoryapp.ui.story.map.StoryLocationActivity
import com.bumptech.glide.Glide

class StoryDetailActivity : AppCompatActivity() {
    private lateinit var story: Story

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupData(binding)
    }

    private fun setupData(binding: ActivityStoryDetailBinding) {
        story = intent.getParcelableExtra<Story>(EXTRA_STORY) as Story
        binding.textViewNameStoryDetail.text = story.name
        binding.textViewDescriptionStoryDetail.text = story.description

        Glide.with(applicationContext)
            .load(story.photoUrl)
            .circleCrop()
            .into(binding.imageViewStoryDetail)
    }

//    private fun setupAction() = locationButton.setOnClickListener {
//        val intent = Intent(this, StoryLocationActivity::class.java)
//        intent.putExtra(EXTRA_STORY, story)
//        startActivity(intent)
//    }

    companion object {
        const val EXTRA_STORY = "EXTRA_STORY"
    }
}