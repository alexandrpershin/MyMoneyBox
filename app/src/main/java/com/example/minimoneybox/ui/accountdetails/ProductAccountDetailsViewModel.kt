package com.example.minimoneybox.ui.accountdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.R
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.repository.InvestorProductsRepository
import com.example.minimoneybox.ui.BaseViewModel
import com.example.minimoneybox.ui.MessageType
import com.example.minimoneybox.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ProductAccountDetailsViewModel(
    private val productId: Int,
    private val productsRepository: InvestorProductsRepository
) : BaseViewModel() {

    private val _playAnimationLiveData = SingleLiveEvent<Boolean>()
    val playAnimationLiveData: LiveData<Boolean>
        get() = _playAnimationLiveData

    fun getInvestorProductModel() =
        productsRepository.getInvestorProductLiveData().switchMap { data ->
            val result = data.productAccounts.find { it.id == productId }
            return@switchMap MutableLiveData(result)
        }

    fun addMoneyToMoneyBox(amount: Int) {
        viewModelScope.launch {
            showLoading()

            val response = productsRepository.payToMoneybox(amount, productId)

            when (response) {
                is TaskResult.ErrorResult -> {
                    hideLoading()
                    notifyError(response.errorType)
                }
                is TaskResult.SuccessResult -> {
                    hideLoading()
                    showMessage(MessageType.Success(R.string.message_payment_success))
                    updateProductInDb(response.data.amount)
                }
            }
        }
    }

    private fun updateProductInDb(amount: Double) {
        viewModelScope.launch {
            val product = productsRepository.getInvestorProductFromDb()
            val result = product.productAccounts.find { it.id == productId }

            result?.let {
                result.moneybox = amount
                productsRepository.saveInvestorProductToDb(product)
                _playAnimationLiveData.value = true
            }

        }
    }

}