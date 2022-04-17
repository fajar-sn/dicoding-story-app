package com.bangkit.intermediate.dicodingstoryapp.ui.story_list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.commit
import com.bangkit.intermediate.dicodingstoryapp.R
import com.bangkit.intermediate.dicodingstoryapp.databinding.ActivityBaseBinding
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.settings.SettingsActivity
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.BaseActivity

class StoryListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) return
        setupView(binding)
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

    override fun setupView(viewBinding: Any) {
        val fragment =
            supportFragmentManager.findFragmentByTag(StoryListFragment::class.java.simpleName)

        if (fragment is StoryListFragment) return
        val storyListFragment = StoryListFragment()

        supportFragmentManager.commit {
            add(R.id.frame_layout_base, storyListFragment, StoryListFragment::class.java.simpleName)
        }
    }

    override fun setupViewModel() {}

    override fun setupAction() {}
}