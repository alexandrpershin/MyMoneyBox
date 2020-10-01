package com.example.minimoneybox.repository

import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.response.InvestorProductsResponse
import com.example.minimoneybox.api.response.ProductPaymentResponse


interface InvestorProductsRepository {
    suspend fun getProducts(): TaskResult<InvestorProductsResponse>
    suspend fun payToMoneybox(amount: Int, productId: Int): TaskResult<ProductPaymentResponse>
}