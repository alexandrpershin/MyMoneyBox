package com.example.minimoneybox.ui.login

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.R
import com.example.minimoneybox.api.ErrorType
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.response.LoginResponse
import com.example.minimoneybox.extensions.getString
import com.example.minimoneybox.model.User
import com.example.minimoneybox.preferences.SecuredSharedPreferences
import com.example.minimoneybox.repository.AuthRepository
import com.example.minimoneybox.repository.InvestorProductsRepository
import com.example.minimoneybox.ui.BaseViewModel
import com.example.minimoneybox.utils.ScreenDirections
import kotlinx.coroutines.launch

class LoginViewModel(
    private val preferences: SecuredSharedPreferences,
    private val authRepository: AuthRepository,
    private val productsRepository: InvestorProductsRepository
) : BaseViewModel() {

    init {
        clearPreviousSaveData()
    }

    /**
     * In case of token expiration the app will force log out user, navigate to [LoginFragment] and clears all saved data
     * see [BaseFragment.handleTokenExpiration]
     * */

    private fun clearPreviousSaveData() {
        viewModelScope.launch {
            preferences.clear()
            authRepository.deleteUser()
            productsRepository.deleteUserAccounts()
        }
    }

    fun logIn(email: String, password: String, userName: String) {
        val valid = isUserInputValid(email, password)
        if (valid) {
            viewModelScope.launch {
                showLoading()

                val result: TaskResult<LoginResponse> = authRepository.login(email, password)

                when (result) {
                    is TaskResult.SuccessResult -> {
                        hideSoftKeyboard()
                        hideLoading()
                        preferences.isLoggedIn = true
                        preferences.token = result.data.session.bearerToken

                        val newUser = User(userName = userName)
                        authRepository.saveNewUserToDb(newUser)

                        goFirstScreen(ScreenDirections.USER_ACCOUNTS_FRAGMENT)
                    }
                    is TaskResult.ErrorResult -> {
                        hideSoftKeyboard()
                        notifyError(result.errorType)
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

