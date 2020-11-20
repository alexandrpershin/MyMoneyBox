package com.example.minimoneybox.api.request

data class ProductPaymentRequest(
    val amount: Int,
    val productId: Int
)