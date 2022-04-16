package com.bangkit.intermediate.dicodingstoryapp.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bangkit.intermediate.dicodingstoryapp.data.remote.request.LoginRequest
import com.bangkit.intermediate.dicodingstoryapp.data.repository.Result
import com.bangkit.intermediate.dicodingstoryapp.databinding.ActivityLoginBinding
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.AuthViewModel
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.BaseActivity
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.register.RegisterActivity
import com.bangkit.intermediate.dicodingstoryapp.ui.component.CustomEmailEditText
import com.bangkit.intermediate.dicodingstoryapp.ui.component.CustomPasswordEditText
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.FormValidator
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.ViewModelFactory
import com.bangkit.intermediate.dicodingstoryapp.ui.story_list.StoryListActivity

class LoginActivity : BaseActivity() {
    private lateinit var emailEditText: CustomEmailEditText
    private lateinit var passwordEditText: CustomPasswordEditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView(binding)
        setupViewModel()
        setupAction()
    }

    override fun setupView(viewBinding: Any) {
        val binding = viewBinding as ActivityLoginBinding
        emailEditText = binding.customEmailEditText
        passwordEditText = binding.customPasswordEditText
        loginButton = binding.loginButton
        registerTextView = binding.registerTextView
        progressBar = binding.loginProgressBar
        setLoginButtonEnable()
        supportActionBar?.hide()
    }

    private fun setLoginButtonEnable() {
        val email = emailEditText.text
        val password = passwordEditText.text

        loginButton.isEnabled = email != null && email.toString()
            .isNotEmpty() && FormValidator.validateEmail(email.toString()) &&
                password != null && password.toString()
            .isNotEmpty() && FormValidator.validatePassword(
            passwordEditText.toString())
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        val viewModel: AuthViewModel by viewModels { factory }
        this.viewModel = viewModel
    }

    override fun setupAction() {
        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setLoginButtonEnable()
            }
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setLoginButtonEnable()
            }
        })

        registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = "${emailEditText.text?.trim()}"
            val password = "${passwordEditText.text?.trim()}"
            val request = LoginRequest(email, password)

            viewModel.login(request).observe(this) { result ->
                if (result == null) return@observe

                when (result) {
                    is Result.Loading -> showLoading(loginButton, progressBar)
                    is Result.Error -> showError(loginButton, progressBar, result.error)
                    is Result.Success -> {
                        finishLoading(loginButton, progressBar)
                        val intent = Intent(this, StoryListActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}