package com.example.minimoneybox.service

import com.example.minimoneybox.api.request.ProductPaymentRequest
import com.example.minimoneybox.api.response.InvestorProductsResponse
import com.example.minimoneybox.api.response.ProductPaymentResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InvestorProductsService {
    @GET("investorproducts/")
    fun getProducts(): Deferred<Response<InvestorProductsResponse>>

    @POST("oneoffpayments/")
    fun payToMoneybox(@Body productPaymentRequest: ProductPaymentRequest): Deferred<Response<ProductPaymentResponse>>

}