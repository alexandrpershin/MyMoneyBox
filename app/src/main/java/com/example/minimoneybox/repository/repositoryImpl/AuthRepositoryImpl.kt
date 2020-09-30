package com.example.minimoneybox.repository.repositoryImpl

import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.executeAsyncRequest
import com.example.minimoneybox.api.request.LoginRequest
import com.example.minimoneybox.api.response.LoginResponse
import com.example.minimoneybox.repository.AuthRepository
import com.example.minimoneybox.service.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {
    override suspend fun login(email: String, password: String): TaskResult<LoginResponse> = withContext(Dispatchers.Default) {
        val response = executeAsyncRequest(authService.login(LoginRequest(email, password)))

        return@withContext response
    }
}

