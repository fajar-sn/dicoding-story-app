package com.bangkit.intermediate.dicodingstoryapp.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import com.bangkit.intermediate.dicodingstoryapp.data.remote.request.LoginRequest
import com.bangkit.intermediate.dicodingstoryapp.data.repository.Result
import com.bangkit.intermediate.dicodingstoryapp.databinding.ActivityLoginBinding
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.LoginViewModel
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.register.RegisterActivity
import com.bangkit.intermediate.dicodingstoryapp.ui.component.CustomEmailEditText
import com.bangkit.intermediate.dicodingstoryapp.ui.component.CustomPasswordEditText
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.BaseActivity
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.FormValidator
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.ViewHelper
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.ViewModelFactory
import com.bangkit.intermediate.dicodingstoryapp.ui.story.story_list.StoryListActivity

class LoginActivity : BaseActivity() {
    private lateinit var emailEditText: CustomEmailEditText
    private lateinit var passwordEditText: CustomPasswordEditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView
    private lateinit var progressBar: ProgressBar
    private var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.savedInstanceState = savedInstanceState
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

        if (savedInstanceState != null) {
            val savedInstanceState = savedInstanceState as Bundle
            val emailText = savedInstanceState.getString(STATE_EMAIL)
            val passwordText = savedInstanceState.getString(STATE_PASSWORD)
            if (emailText != null) emailEditText.setText("$emailText")
            if (passwordText != null) passwordEditText.setText("$passwordText")
        }
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
        val factory = ViewModelFactory.getAuthInstance(this)
        val viewModel: LoginViewModel by viewModels { factory }
        this.viewModel = viewModel
    }

    override fun setupAction() {
        val textWatcher = ViewHelper.addTextChangeListener { setLoginButtonEnable() }
        emailEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = "${emailEditText.text?.trim()}"
            val password = "${passwordEditText.text?.trim()}"
            val request = LoginRequest(email, password)
            val viewModel = this.viewModel as LoginViewModel

            viewModel.login(request).observe(this) { result ->
                if (result == null) return@observe

                when (result) {
                    is Result.Loading -> showLoading(loginButton, progressBar)
                    is Result.Error -> showError(loginButton, progressBar, result.error)
                    is Result.Success -> {
                        finishLoading(loginButton, progressBar)
                        viewModel.saveUserToken(result.data.token)
                        val intent = Intent(this, StoryListActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val emailText = emailEditText.text
        val passwordText = passwordEditText.text

        if (!(emailText.isNullOrBlank() || emailText.isNullOrEmpty()))
            outState.putString(STATE_EMAIL, "$emailText")

        if (!(passwordText.isNullOrBlank() || passwordText.isNullOrEmpty()))
            outState.putString(STATE_PASSWORD, "$passwordText")
    }

    companion object {
        internal const val STATE_EMAIL = "state_email"
        internal const val STATE_PASSWORD = "state_password"
    }
}