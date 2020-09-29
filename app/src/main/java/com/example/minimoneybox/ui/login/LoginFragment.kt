package com.example.minimoneybox.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.minimoneybox.R
import com.example.minimoneybox.databinding.FragmentLoginBinding
import com.example.minimoneybox.ui.BaseFragment
import com.example.minimoneybox.ui.ErrorType
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModel()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun initComponents(binding: FragmentLoginBinding) {

    }


    override fun addListeners(binding: FragmentLoginBinding) {
        with(binding) {
            btnSignIn.setOnClickListener {
                val directions = LoginFragmentDirections.fromLoginFragmentToUserAccountsFragment()
                viewModel.goFirstScreen(directions, R.id.loginFragment)
            }
        }
    }

    override fun addObservers(binding: FragmentLoginBinding) {
    }

    override fun errorHandler(errorType: ErrorType) {
    }

}
