package com.example.minimoneybox.ui.login

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.minimoneybox.api.ErrorType
import com.example.minimoneybox.databinding.FragmentLoginBinding
import com.example.minimoneybox.extensions.showErrorMessage
import com.example.minimoneybox.ui.BaseFragment
import com.example.minimoneybox.utils.textvalidator.EmailTextValidator
import com.example.minimoneybox.utils.textvalidator.EmailValidator
import com.example.minimoneybox.utils.textvalidator.PasswordTextValidator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModel()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun initComponents(binding: FragmentLoginBinding) {
    }

    override fun addListeners(binding: FragmentLoginBinding) {
        with(binding) {
            etEmail.addTextChangedListener(
                EmailTextValidator(requireContext(), tilEmail, viewModel.emailValidator)
            )
            etPassword.addTextChangedListener(PasswordTextValidator(requireContext(), tilPassword))

            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val userName = etName.text.toString()
                viewModel.logIn(email, password, userName)
            }
        }
    }

    override fun addObservers(binding: FragmentLoginBinding) {
    }

    override fun errorHandler(errorType: ErrorType) {
        when (errorType) {
            is ErrorType.LoginError -> {
                showErrorMessage(errorType.message ?: getString(errorType.resId))
            }

            is ErrorType.InputError -> {
                showErrorMessage(getString(errorType.resId))
            }

            is ErrorType.GenericError -> {
                showErrorMessage(errorType.message ?: getString(errorType.resId))
            }
            is ErrorType.InternetError -> {
                showErrorMessage(getString(errorType.resId))
            }
        }
    }

}

