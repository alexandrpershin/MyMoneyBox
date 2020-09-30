package com.example.minimoneybox.service

import com.example.minimoneybox.api.request.LoginRequest
import com.example.minimoneybox.api.response.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("users/login/")
    fun login(@Body loginRequest: LoginRequest): Deferred<Response<LoginResponse>>
}
