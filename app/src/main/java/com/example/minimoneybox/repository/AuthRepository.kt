package com.example.minimoneybox.repository

import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.response.LoginResponse


interface AuthRepository {
    suspend fun login(email: String, password: String): TaskResult<LoginResponse>
}