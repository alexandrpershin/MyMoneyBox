package com.example.minimoneybox.api.response

import com.google.gson.annotations.SerializedName

data class CommonServerErrorResponse(
    @SerializedName("Message") val message: String,
    @SerializedName("Name") val name: String,
    @SerializedName("ValidationErrors") val validationErrors: List<Any>
)