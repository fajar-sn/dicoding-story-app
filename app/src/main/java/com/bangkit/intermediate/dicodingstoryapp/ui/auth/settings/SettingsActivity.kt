package com.bangkit.intermediate.dicodingstoryapp.ui.auth.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.bangkit.intermediate.dicodingstoryapp.R
import com.bangkit.intermediate.dicodingstoryapp.databinding.ActivityBaseBinding

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) return
        val fragment =
            supportFragmentManager.findFragmentByTag(SettingsFragment::class.java.simpleName)

        if (fragment is SettingsFragment) return
        val settingsFragment = SettingsFragment()

        supportFragmentManager.commit {
            add(R.id.frame_layout_base, settingsFragment, SettingsFragment::class.java.simpleName)
        }
        supportActionBar?.title = getString(R.string.settings)
    }
}
