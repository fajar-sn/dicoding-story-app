package com.bangkit.intermediate.dicodingstoryapp.ui.auth.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.intermediate.dicodingstoryapp.databinding.ActivityLoginBinding
import com.bangkit.intermediate.dicodingstoryapp.ui.component.CustomEmailEditText
import com.bangkit.intermediate.dicodingstoryapp.ui.component.CustomPasswordEditText
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.FormValidator

class LoginActivity : AppCompatActivity() {
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

    private fun setupView(binding: ActivityLoginBinding) {
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

    private fun setupAction() {
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
            Toast.makeText(this, "TO REGISTER", Toast.LENGTH_SHORT).show()
        }

        loginButton.setOnClickListener {
            Toast.makeText(this, "SUBMITTING", Toast.LENGTH_SHORT).show()
        }
    }
}