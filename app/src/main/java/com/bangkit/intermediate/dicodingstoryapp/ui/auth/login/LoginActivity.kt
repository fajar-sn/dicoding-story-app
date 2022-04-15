package com.bangkit.intermediate.dicodingstoryapp.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.bangkit.intermediate.dicodingstoryapp.databinding.ActivityLoginBinding
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.BaseActivity
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.register.RegisterActivity
import com.bangkit.intermediate.dicodingstoryapp.ui.component.CustomEmailEditText
import com.bangkit.intermediate.dicodingstoryapp.ui.component.CustomPasswordEditText
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.FormValidator
import com.bangkit.intermediate.dicodingstoryapp.ui.story_list.StoryListActivity

class LoginActivity : BaseActivity() {
    private lateinit var emailEditText: CustomEmailEditText
    private lateinit var passwordEditText: CustomPasswordEditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView(binding)
        setupAction()
    }

    override fun setupView(viewBinding: Any) {
        val binding = viewBinding as ActivityLoginBinding
        emailEditText = binding.customEmailEditText
        passwordEditText = binding.customPasswordEditText
        loginButton = binding.loginButton
        registerTextView = binding.registerTextView
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
        TODO("Not yet implemented")
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
            val intent = Intent(this, StoryListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}