package com.bangkit.intermediate.dicodingstoryapp.ui.story.add_story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.bangkit.intermediate.dicodingstoryapp.R
import com.bangkit.intermediate.dicodingstoryapp.databinding.ActivityBaseBinding

class AddStoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) return
        setupView()
    }

    private fun setupView() {
        val addStoryFragmentSimpleName = AddStoryFragment::class.java.simpleName
        val fragment = supportFragmentManager.findFragmentByTag(addStoryFragmentSimpleName)

        if (fragment is AddStoryFragment) return
        val addStoryFragment = AddStoryFragment()

        supportFragmentManager.commit {
            add(R.id.frame_layout_base, addStoryFragment, addStoryFragmentSimpleName)
        }
    }
}