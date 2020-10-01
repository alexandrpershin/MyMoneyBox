package com.example.minimoneybox.service

import com.example.minimoneybox.api.response.InvestorProductsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface InvestorProductsService {
    @GET("investorproducts/")
    fun getProducts(): Deferred<Response<InvestorProductsResponse>>
}