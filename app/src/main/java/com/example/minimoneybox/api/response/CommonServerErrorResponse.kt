package com.example.minimoneybox.api.response

data class CommonServerErrorResponse(
    val message: String,
    val name: String,
    val validationErrors: List<Any>
)