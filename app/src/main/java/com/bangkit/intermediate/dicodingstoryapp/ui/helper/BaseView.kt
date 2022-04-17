package com.bangkit.intermediate.dicodingstoryapp.ui.helper

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

interface BaseView {
    fun setupView(viewBinding: Any)
    fun setupViewModel()
    fun setupAction()
}

object ViewHelper {
    fun showLoading(button: Button, progressBar: ProgressBar) {
        button.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    fun finishLoading(button: Button, progressBar: ProgressBar) {
        button.isEnabled = true
        progressBar.visibility = View.GONE
    }

    fun showError(context: Context, button: Button, progressBar: ProgressBar, message: String) {
        finishLoading(button, progressBar)
        Toast.makeText(context, "Something went wrong. $message", Toast.LENGTH_SHORT).show()
    }

    fun addTextChangeListener(callback: () -> Unit) = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(s: Editable?) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
            callback()
    }
}

abstract class BaseActivity : AppCompatActivity(), BaseView {
    protected lateinit var viewModel: ViewModel

    protected fun showLoading(button: Button, progressBar: ProgressBar) =
        ViewHelper.showLoading(button, progressBar)

    protected fun finishLoading(button: Button, progressBar: ProgressBar) =
        ViewHelper.finishLoading(button, progressBar)

    protected fun showError(button: Button, progressBar: ProgressBar, message: String) =
        ViewHelper.showError(this, button, progressBar, message)
}

abstract class BaseFragment : Fragment(), BaseView {
    protected lateinit var viewModel: ViewModel
    protected var viewBinding: ViewBinding? = null
    protected val binding get() = viewBinding!!

    protected fun showLoading(button: Button, progressBar: ProgressBar) =
        ViewHelper.showLoading(button, progressBar)

    protected fun finishLoading(button: Button, progressBar: ProgressBar) =
        ViewHelper.finishLoading(button, progressBar)

    protected fun showError(button: Button, progressBar: ProgressBar, message: String) =
        ViewHelper.showError(requireActivity(), button, progressBar, message)
}
