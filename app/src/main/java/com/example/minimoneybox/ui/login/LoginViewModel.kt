package com.example.minimoneybox.ui.login

import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.R
import com.example.minimoneybox.api.ErrorType
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.response.LoginResponse
import com.example.minimoneybox.model.User
import com.example.minimoneybox.preferences.UserPreferences
import com.example.minimoneybox.repository.AuthRepository
import com.example.minimoneybox.repository.InvestorProductsRepository
import com.example.minimoneybox.ui.BaseViewModel
import com.example.minimoneybox.utils.textvalidator.EmailValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(
    private val preferences: UserPreferences,
    private val authRepository: AuthRepository,
    private val productsRepository: InvestorProductsRepository,
    val emailValidator: EmailValidator,
    private val coroutineContext: CoroutineContext = Dispatchers.Main
) : BaseViewModel() {

    init {
        clearPreviousSaveData()
    }

    /**
     * In case of token expiration the app will force log out user, navigate to [LoginFragment] and clears all saved data
     * see [BaseFragment.handleTokenExpiration]
     * */

    private fun clearPreviousSaveData() {
        viewModelScope.launch(coroutineContext) {
            preferences.clear()
            authRepository.deleteUser()
            productsRepository.deleteInvestorProductFromDb()
        }
    }

    fun logIn(email: String, password: String, userName: String) {
        val valid = isUserInputValid(email, password)
        if (valid) {
            viewModelScope.launch(coroutineContext) {
                showLoading()

                val result: TaskResult<LoginResponse> = authRepository.login(email, password)

                when (result) {
                    is TaskResult.SuccessResult -> {
                        hideSoftKeyboard()
                        hideLoading()
                        preferences.isLoggedIn = true
                        preferences.token = result.data.session.token

                        val newUser = User(userName = userName)
                        authRepository.saveNewUserToDb(newUser)

                        goTo(LoginFragmentDirections.loginFragmentToMainGraph())
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
                notifyError(ErrorType.InputError(R.string.error_empty_email))
                false
            }

            !emailValidator.isValid(email) -> {
                notifyError(ErrorType.InputError(R.string.email_address_error))
                false
            }

            password.isEmpty() -> {
                notifyError(ErrorType.InputError(R.string.error_empty_password))
                false
            }

            else -> true
        }
    }
}

