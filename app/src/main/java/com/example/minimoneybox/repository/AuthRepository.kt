package com.example.minimoneybox.repository

import androidx.lifecycle.LiveData
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.response.LoginResponse
import com.example.minimoneybox.model.User


interface AuthRepository {
    suspend fun login(email: String, password: String): TaskResult<LoginResponse>
    suspend fun saveNewUserToDb(user: User)
    suspend fun updateUserToDb(user: User)
    suspend fun getUser(): User
    fun getUserLiveData(): LiveData<User>
    suspend fun deleteUser()
}