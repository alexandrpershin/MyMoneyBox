package com.example.minimoneybox.ui.accountdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.R
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.extensions.getString
import com.example.minimoneybox.repository.AuthRepository
import com.example.minimoneybox.repository.InvestorProductsRepository
import com.example.minimoneybox.ui.BaseViewModel
import com.example.minimoneybox.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class AccountDetailsViewModel(
    private val productId: Int,
    private val authRepository: AuthRepository,
    private val productsRepository: InvestorProductsRepository
) : BaseViewModel() {

    private val _playAnimationLiveData = SingleLiveEvent<Boolean>()
    val playAnimationLiveData: LiveData<Boolean>
        get() = _playAnimationLiveData

    fun getInvestorProductModel() = authRepository.getUserLiveData().switchMap { user ->
        val result = user.accountModel!!.products.find { it.id == productId }!!
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
                    showSnackBar(R.string.message_payment_success.getString())
                    updateProductInDb(response.data.amount)
                }
            }
        }
    }

    private fun updateProductInDb(amount: Double) {
        viewModelScope.launch {
            val user = authRepository.getUser()
            val result = user.accountModel!!.products.find { it.id == productId }!!
            result.moneybox = amount

            authRepository.updateUserToDb(user)
            _playAnimationLiveData.value = true
        }
    }

}