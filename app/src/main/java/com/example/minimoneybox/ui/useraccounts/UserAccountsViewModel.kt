package com.example.minimoneybox.ui.useraccounts

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.response.toUserAccountsModel
import com.example.minimoneybox.model.InvestorProductModel
import com.example.minimoneybox.repository.AuthRepository
import com.example.minimoneybox.repository.InvestorProductsRepository
import com.example.minimoneybox.ui.BaseViewModel
import com.example.minimoneybox.utils.ScreenDirections
import kotlinx.coroutines.launch

class UserAccountsViewModel(
    private val productsRepository: InvestorProductsRepository,
    private val authRepository: AuthRepository
) :
    BaseViewModel() {

    init {
        fetchInvestorProductsFromServer()
    }

    fun fetchInvestorProductsFromServer() {
        viewModelScope.launch {
            showLoading()
            val result = productsRepository.getProducts()

            when (result) {
                is TaskResult.ErrorResult -> {
                    hideLoading()
                    notifyError(result.errorType)
                }
                is TaskResult.SuccessResult -> {
                    hideLoading()
                    val latestAccountModel = result.data.toUserAccountsModel()
                    val user = authRepository.getUser()
                    user.accountModel = latestAccountModel
                    authRepository.updateUserToDb(user)
                }
            }
        }
    }

    fun getUser() = authRepository.getUserLiveData()

    fun goToAccountDetails(model: InvestorProductModel) {
        val bundle = Bundle()
        bundle.putParcelable(KEY_ACCOUNT_DETAILS, model)
        goToWithArgs(ScreenDirections.ACCOUNT_DETAILS_FRAGMENT, bundle)
    }

    companion object {
        const val KEY_ACCOUNT_DETAILS = "account_details"
    }

}
