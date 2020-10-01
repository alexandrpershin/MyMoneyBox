package com.example.minimoneybox.ui.useraccounts

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.response.toUserAccountsModel
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

    fun goToAccountDetails(productId: Int) {
        val bundle = Bundle()
        bundle.putInt(KEY_ACCOUNT_DETAILS_ID, productId)
        goToWithArgs(ScreenDirections.ACCOUNT_DETAILS_FRAGMENT, bundle)
    }

    companion object {
        const val KEY_ACCOUNT_DETAILS_ID = "account_details_id"
    }

}
