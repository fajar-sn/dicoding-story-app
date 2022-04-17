package com.bangkit.intermediate.dicodingstoryapp.ui.auth.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.bangkit.intermediate.dicodingstoryapp.R
import com.bangkit.intermediate.dicodingstoryapp.databinding.FragmentSettingsBinding
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.SettingsViewModel
import com.bangkit.intermediate.dicodingstoryapp.ui.auth.login.LoginActivity
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.BaseFragment
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.ViewModelFactory

class SettingsFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun setupView(viewBinding: Any) {}

    override fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel: SettingsViewModel by viewModels { factory }
        this.viewModel = viewModel
    }

    override fun setupAction() {
        (binding as FragmentSettingsBinding).logoutButton.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireActivity())
            alertDialogBuilder.setMessage(R.string.are_you_sure)
            alertDialogBuilder.setNegativeButton(R.string.no) { _, _ -> }
            alertDialogBuilder.setPositiveButton(R.string.yes) { _, _ ->
                (viewModel as SettingsViewModel).saveUserToken("")
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            alertDialogBuilder.create().show()
        }
    }
}