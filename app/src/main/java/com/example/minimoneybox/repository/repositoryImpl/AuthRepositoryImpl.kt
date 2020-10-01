package com.example.minimoneybox.repository.repositoryImpl

import androidx.lifecycle.LiveData
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.executeAsyncRequest
import com.example.minimoneybox.api.request.LoginRequest
import com.example.minimoneybox.api.response.LoginResponse
import com.example.minimoneybox.model.UserModel
import com.example.minimoneybox.persistence.LocalDatabase
import com.example.minimoneybox.persistence.UserDao
import com.example.minimoneybox.repository.AuthRepository
import com.example.minimoneybox.service.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val localDatabase: LocalDatabase,
    private val userDao: UserDao = localDatabase.userDao()
) : AuthRepository {
    override suspend fun login(email: String, password: String): TaskResult<LoginResponse> =
        withContext(Dispatchers.Default) {
            val response = executeAsyncRequest(authService.login(LoginRequest(email, password)))

            return@withContext response
        }

    override suspend fun saveNewUserToDb(user: UserModel) = withContext(Dispatchers.Default) {
        userDao.insertUser(user)
    }

    override suspend fun getUser(): UserModel = withContext(Dispatchers.Default) {
        return@withContext userDao.getUser()
    }

    override suspend fun updateUserToDb(user: UserModel) = withContext(Dispatchers.Default) {
        userDao.update(user)
    }

    override fun getUserLiveData(): LiveData<UserModel>  = userDao.getUserLiveData()

    override suspend fun deleteUser() = withContext(Dispatchers.Default) {
        userDao.deleteUser()
    }
}

