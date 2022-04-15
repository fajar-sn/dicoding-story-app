package com.bangkit.intermediate.dicodingstoryapp.ui.auth.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.bangkit.intermediate.dicodingstoryapp.databinding.ActivityRegisterBinding
import com.bangkit.intermediate.dicodingstoryapp.ui.BaseActivity
import com.bangkit.intermediate.dicodingstoryapp.ui.component.CustomEmailEditText
import com.bangkit.intermediate.dicodingstoryapp.ui.component.CustomPasswordEditText
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.FormValidator

class RegisterActivity : BaseActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: CustomEmailEditText
    private lateinit var passwordEditText: CustomPasswordEditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView(binding)
        setupAction()
    }

    override fun setupView(viewBinding: Any) {
        val binding = viewBinding as ActivityRegisterBinding
        nameEditText = binding.nameEditText
        emailEditText = binding.emailEditText
        passwordEditText = binding.passwordEditText
        registerButton = binding.registerButton
        supportActionBar?.hide()
        setRegisterButtonEnable()
    }

    override fun setupViewModel() {
        TODO("Not yet implemented")
    }

    override fun setupAction() {
        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setRegisterButtonEnable()
            }
        })

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setRegisterButtonEnable()
            }
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setRegisterButtonEnable()
            }
        })
    }

    private fun setRegisterButtonEnable() {
        val name = nameEditText.text
        val email = emailEditText.text
        val password = passwordEditText.text

        registerButton.isEnabled =
            name != null && name.toString().isNotEmpty() && email != null && email.toString()
                .isNotEmpty() && FormValidator.validateEmail(email.toString()) &&
                    password != null && password.toString()
                .isNotEmpty() && FormValidator.validatePassword(
                passwordEditText.toString())
    }
}