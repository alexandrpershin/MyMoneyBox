package com.example.minimoneybox.repository

import androidx.lifecycle.LiveData
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.response.InvestorProductsResponse
import com.example.minimoneybox.api.response.ProductPaymentResponse
import com.example.minimoneybox.model.UserAccounts


interface InvestorProductsRepository {
    suspend fun getProductsFromServer(): TaskResult<InvestorProductsResponse>
    suspend fun payToMoneybox(amount: Int, productId: Int): TaskResult<ProductPaymentResponse>
    suspend fun saveUserAccounts(userAccounts: UserAccounts)
    fun getUserAccountsLiveData(): LiveData<UserAccounts>
    suspend fun getUserAccounts(): UserAccounts
    suspend fun deleteUserAccounts()
}