package com.example.minimoneybox.api.response

import com.google.gson.annotations.SerializedName

data class ProductPaymentResponse(
    @SerializedName("Moneybox") val amount: Double
)