package com.bangkit.intermediate.dicodingstoryapp.ui.helper

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    protected abstract fun setupView(viewBinding: Any)
    protected abstract fun setupViewModel()
    protected abstract fun setupAction()

    protected fun showLoading(button: Button, progressBar: ProgressBar) {
        button.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    protected fun finishLoading(button: Button, progressBar: ProgressBar) {
        button.isEnabled = true
        progressBar.visibility = View.GONE
    }

    protected fun showError(button: Button, progressBar: ProgressBar, message: String) {
        finishLoading(button, progressBar)
        Toast.makeText(this, "Something went wrong. $message", Toast.LENGTH_SHORT).show()
    }
}
