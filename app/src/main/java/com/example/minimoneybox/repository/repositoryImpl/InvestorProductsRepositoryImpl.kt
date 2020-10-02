package com.example.minimoneybox.repository.repositoryImpl

import androidx.lifecycle.LiveData
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.executeAsyncRequest
import com.example.minimoneybox.api.request.ProductPaymentRequest
import com.example.minimoneybox.api.response.InvestorProductsResponse
import com.example.minimoneybox.api.response.ProductPaymentResponse
import com.example.minimoneybox.model.UserAccounts
import com.example.minimoneybox.persistence.LocalDatabase
import com.example.minimoneybox.persistence.dao.UserAccountsDao
import com.example.minimoneybox.repository.InvestorProductsRepository
import com.example.minimoneybox.service.InvestorProductsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class InvestorProductsRepositoryImpl(
    private val service: InvestorProductsService,
    private val localDatabase: LocalDatabase,
    private val accountsDao: UserAccountsDao = localDatabase.userAccountsDao(),
    private val coroutineContext: CoroutineContext = Dispatchers.Default
) :
    InvestorProductsRepository {
    override suspend fun getProductsFromServer(): TaskResult<InvestorProductsResponse> =
        withContext(coroutineContext) {
            val response = executeAsyncRequest(service.getProducts())
            return@withContext response
        }

    override suspend fun payToMoneybox(
        amount: Int,
        productId: Int
    ): TaskResult<ProductPaymentResponse> =
        withContext(coroutineContext) {
            val productPaymentRequest = ProductPaymentRequest(amount, productId)
            val response = executeAsyncRequest(service.payToMoneybox(productPaymentRequest))
            return@withContext response
        }

    override suspend fun saveUserAccounts(userAccounts: UserAccounts) =
        withContext(coroutineContext) {
            accountsDao.saveUserAccounts(userAccounts)
        }

    override fun getUserAccountsLiveData(): LiveData<UserAccounts> {
        return accountsDao.getUserAccountsLiveData()
    }

    override suspend fun deleteUserAccounts() =
        withContext(coroutineContext) {
            accountsDao.deleteUserAccounts()
        }

    override suspend fun getUserAccounts(): UserAccounts = withContext(coroutineContext) {
        return@withContext accountsDao.getUserAccounts()
    }
}