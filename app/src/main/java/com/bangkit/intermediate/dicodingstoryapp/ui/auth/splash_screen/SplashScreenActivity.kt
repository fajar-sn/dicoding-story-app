package com.bangkit.intermediate.dicodingstoryapp.ui.auth.splash_screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.SplashScreenViewModel
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.login.LoginActivity
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.BaseActivity
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.ViewModelFactory
import com.bangkit.intermediate.dicodingstoryapp.ui.story.story_list.StoryListActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupAction()
    }

    override fun setupView(viewBinding: Any) {}

    override fun setupViewModel() {
        val factory = ViewModelFactory.getAuthInstance(this)
        val viewModel: SplashScreenViewModel by viewModels { factory }
        this.viewModel = viewModel
    }

    override fun setupAction() {
        val viewModel = this.viewModel as SplashScreenViewModel

        lifecycleScope.launch(Dispatchers.Default) {
            delay(1000)

            withContext(Dispatchers.Main) {
                viewModel.getUserToken().observe(this@SplashScreenActivity) {
                    if (it.isNullOrBlank() || it.isNullOrEmpty()) {
                        val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent =
                            Intent(this@SplashScreenActivity, StoryListActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}