package com.example.minimoneybox.repository

import androidx.lifecycle.LiveData
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.response.LoginResponse
import com.example.minimoneybox.model.UserModel


interface AuthRepository {
    suspend fun login(email: String, password: String): TaskResult<LoginResponse>
    suspend fun saveNewUserToDb(user: UserModel)
    suspend fun updateUserToDb(user: UserModel)
    suspend fun getUser(): UserModel
    fun getUserLiveData(): LiveData<UserModel>
    suspend fun deleteUser()
}