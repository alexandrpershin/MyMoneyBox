package com.example.minimoneybox.ui.useraccounts

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.response.toInvestorProductModel
import com.example.minimoneybox.model.InvestorProduct
import com.example.minimoneybox.model.User
import com.example.minimoneybox.repository.AuthRepository
import com.example.minimoneybox.repository.InvestorProductsRepository
import com.example.minimoneybox.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserAccountsViewModel(
    private val productsRepository: InvestorProductsRepository,
    private val authRepository: AuthRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.Main
) :
    BaseViewModel() {

    init {
        fetchInvestorProductsFromServer()
    }

    fun fetchInvestorProductsFromServer() {
        viewModelScope.launch(coroutineContext) {
            showLoading()
            val result = productsRepository.getProductsFromServer()

            when (result) {
                is TaskResult.ErrorResult -> {
                    hideLoading()
                    notifyError(result.errorType)
                }
                is TaskResult.SuccessResult -> {
                    hideLoading()
                    val investorProduct = result.data.toInvestorProductModel()
                    productsRepository.saveInvestorProductToDb(investorProduct)
                }
            }
        }
    }

    fun getUser(): LiveData<User> = authRepository.getUserLiveData()

    fun getInvestorProduct(): LiveData<InvestorProduct> =
        productsRepository.getInvestorProductLiveData()

}
