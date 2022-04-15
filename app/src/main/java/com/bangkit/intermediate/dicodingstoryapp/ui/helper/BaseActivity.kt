package com.bangkit.intermediate.dicodingstoryapp.ui.helper

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {
    protected abstract fun setupView(viewBinding: Any)
    protected abstract fun setupViewModel()
    protected abstract fun setupAction()
}
