package com.example.minimoneybox.repository

import androidx.lifecycle.LiveData
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.response.InvestorProductsResponse
import com.example.minimoneybox.api.response.ProductPaymentResponse
import com.example.minimoneybox.model.InvestorProduct


interface InvestorProductsRepository {
    suspend fun getProductsFromServer(): TaskResult<InvestorProductsResponse>
    suspend fun payToMoneybox(amount: Int, productId: Int): TaskResult<ProductPaymentResponse>
    suspend fun saveInvestorProductToDb(investorProduct: InvestorProduct)
    fun getInvestorProductLiveData(): LiveData<InvestorProduct>
    suspend fun getInvestorProductFromDb(): InvestorProduct
    suspend fun deleteInvestorProductFromDb()
}