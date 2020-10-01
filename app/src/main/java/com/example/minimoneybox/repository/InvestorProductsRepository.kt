package com.example.minimoneybox.repository

import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.response.InvestorProductsResponse


interface InvestorProductsRepository {
    suspend fun getProducts(): TaskResult<InvestorProductsResponse>
}