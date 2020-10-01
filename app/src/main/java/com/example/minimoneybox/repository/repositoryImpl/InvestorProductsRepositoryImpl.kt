package com.example.minimoneybox.repository.repositoryImpl

import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.executeAsyncRequest
import com.example.minimoneybox.api.request.ProductPaymentRequest
import com.example.minimoneybox.api.response.InvestorProductsResponse
import com.example.minimoneybox.api.response.ProductPaymentResponse
import com.example.minimoneybox.repository.InvestorProductsRepository
import com.example.minimoneybox.service.InvestorProductsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class InvestorProductsRepositoryImpl(private val service: InvestorProductsService) :
    InvestorProductsRepository {
    override suspend fun getProducts(): TaskResult<InvestorProductsResponse> =
        withContext(Dispatchers.Default) {
            val response = executeAsyncRequest(service.getProducts())
            return@withContext response
        }

    override suspend fun payToMoneybox(amount: Int, productId: Int): TaskResult<ProductPaymentResponse> =
        withContext(Dispatchers.Default) {
            val productPaymentRequest = ProductPaymentRequest(amount, productId)
            val response = executeAsyncRequest(service.payToMoneybox(productPaymentRequest))
            return@withContext response
        }
}