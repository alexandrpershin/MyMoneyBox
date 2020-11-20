package com.example.minimoneybox.api.request

class LoginRequest(
    val login: String,
    val password: String,
    val name: String = ""
)