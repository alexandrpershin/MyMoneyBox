package com.example.minimoneybox.ui.login

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.R
import com.example.minimoneybox.api.ErrorType
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.doOnError
import com.example.minimoneybox.api.doOnSuccess
import com.example.minimoneybox.api.response.LoginResponse
import com.example.minimoneybox.api.response.toUserModel
import com.example.minimoneybox.extensions.getString
import com.example.minimoneybox.preferences.SecuredSharedPreferences
import com.example.minimoneybox.repository.AuthRepository
import com.example.minimoneybox.ui.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val preferences: SecuredSharedPreferences,
    private val authRepository: AuthRepository
) : BaseViewModel() {


    fun logIn(email: String, password: String, userName: String) {
        val valid = isUserInputValid(email, password)
        if (valid) {
            viewModelScope.launch {
                showLoading()

                val result: TaskResult<LoginResponse> = authRepository.login(email, password)

                when (result) {
                    is TaskResult.SuccessResult -> {
                        hideLoading()

                        val user = result.data.toUserModel()
                        preferences.isLoggedIn = true
                        preferences.token = user.token
                        preferences.userName = userName

                        goFirstScreen(
                            LoginFragmentDirections.fromLoginFragmentToUserAccountsFragment(),
                            R.id.loginFragment
                        )

                    }
                    is TaskResult.ErrorResult -> {
                        notifyError(result.type)
                        hideLoading()

                    }
                }

            }
        }

    }

    private fun isUserInputValid(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                notifyError(ErrorType.InputError(R.string.error_empty_email.getString()))
                false
            }

            !Patterns.EMAIL_ADDRESS.toRegex().matches(email) -> {
                notifyError(ErrorType.InputError(R.string.email_address_error.getString()))
                false
            }

            password.isEmpty() -> {
                notifyError(ErrorType.InputError(R.string.error_empty_password.getString()))
                false
            }

            else -> true
        }
    }
}

