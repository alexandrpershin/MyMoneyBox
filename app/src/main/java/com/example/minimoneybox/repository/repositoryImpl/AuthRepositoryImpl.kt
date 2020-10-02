package com.example.minimoneybox.repository.repositoryImpl

import androidx.lifecycle.LiveData
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.executeAsyncRequest
import com.example.minimoneybox.api.request.LoginRequest
import com.example.minimoneybox.api.response.LoginResponse
import com.example.minimoneybox.model.User
import com.example.minimoneybox.persistence.LocalDatabase
import com.example.minimoneybox.persistence.dao.UserDao
import com.example.minimoneybox.repository.AuthRepository
import com.example.minimoneybox.service.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val localDatabase: LocalDatabase,
    private val userDao: UserDao = localDatabase.userDao(),
    private val coroutineContext: CoroutineContext = Dispatchers.Default
) : AuthRepository {
    override suspend fun login(email: String, password: String): TaskResult<LoginResponse> =
        withContext(coroutineContext) {
            val response = executeAsyncRequest(authService.login(LoginRequest(email, password)))

            return@withContext response
        }

    override suspend fun saveNewUserToDb(user: User) = withContext(coroutineContext) {
        userDao.insertUser(user)
    }

    override suspend fun getUser(): User = withContext(coroutineContext) {
        return@withContext userDao.getUser()
    }

    override suspend fun updateUserToDb(user: User) = withContext(coroutineContext) {
        userDao.update(user)
    }

    override fun getUserLiveData(): LiveData<User>  = userDao.getUserLiveData()

    override suspend fun deleteUser() = withContext(coroutineContext) {
        userDao.deleteUser()
    }
}

