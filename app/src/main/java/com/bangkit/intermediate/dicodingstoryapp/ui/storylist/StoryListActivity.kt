package com.bangkit.intermediate.dicodingstoryapp.ui.storylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.bangkit.intermediate.dicodingstoryapp.R
import com.bangkit.intermediate.dicodingstoryapp.databinding.ActivityStoryListBinding
import com.bangkit.intermediate.dicodingstoryapp.ui.settings.SettingsActivity

class StoryListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        if (item.itemId != R.id.menu_settings) false else {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            true
        }
}