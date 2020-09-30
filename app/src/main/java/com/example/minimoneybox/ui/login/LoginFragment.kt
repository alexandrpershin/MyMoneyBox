package com.example.minimoneybox.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.minimoneybox.R
import com.example.minimoneybox.api.ErrorType
import com.example.minimoneybox.databinding.FragmentLoginBinding
import com.example.minimoneybox.extensions.getString
import com.example.minimoneybox.extensions.showErrorMessage
import com.example.minimoneybox.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModel()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun initComponents(binding: FragmentLoginBinding) {
        progressBar = binding.partialProgress.progressBar
    }

    override fun addListeners(binding: FragmentLoginBinding) {
        with(binding) {
            btnSignIn.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val userName = binding.etName.text.toString()
                viewModel.logIn(email, password, userName)
            }
        }
    }

    override fun addObservers(binding: FragmentLoginBinding) {
    }

    override fun errorHandler(errorType: ErrorType) {
        when (errorType) {
            is ErrorType.LoginError -> {
                showErrorMessage(errorType.message)
            }

            is ErrorType.InputError -> {
                showErrorMessage(errorType.message)
            }

            is ErrorType.GenericError -> {
                showErrorMessage(
                    errorType.exception.message
                        ?: R.string.error_message_unexpected_error.getString()
                )
            }
            is ErrorType.InternetError -> {
                showErrorMessage(errorType.message)
            }
        }
    }

}
