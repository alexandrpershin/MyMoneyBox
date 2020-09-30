package com.example.minimoneybox.model

class UserModel(
    val token: String,
    val email: String,
    val firstName: String? = null,
    val lastName: String? = null
)