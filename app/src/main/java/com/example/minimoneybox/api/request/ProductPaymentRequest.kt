package com.example.minimoneybox.api.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductPaymentRequest(
    @SerializedName("Amount") val amount: Int,
    @SerializedName("InvestorProductId") val productId: Int
) : Serializable