package com.example.minimoneybox.repository.repositoryImpl

import androidx.lifecycle.LiveData
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.executeAsyncRequest
import com.example.minimoneybox.api.request.ProductPaymentRequest
import com.example.minimoneybox.api.response.InvestorProductsResponse
import com.example.minimoneybox.api.response.ProductPaymentResponse
import com.example.minimoneybox.model.InvestorProduct
import com.example.minimoneybox.persistence.LocalDatabase
import com.example.minimoneybox.persistence.dao.InvestorProductDao
import com.example.minimoneybox.repository.InvestorProductsRepository
import com.example.minimoneybox.service.InvestorProductsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class InvestorProductsRepositoryImpl(
    private val service: InvestorProductsService,
    private val localDatabase: LocalDatabase,
    private val productDao: InvestorProductDao = localDatabase.investorProductDao(),
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

    override suspend fun saveInvestorProductToDb(investorProduct: InvestorProduct) =
        withContext(coroutineContext) {
            productDao.saveInvestorProduct(investorProduct)
        }

    override fun getInvestorProductLiveData(): LiveData<InvestorProduct> {
        return productDao.getInvestorProductLiveData()
    }

    override suspend fun deleteInvestorProductFromDb() =
        withContext(coroutineContext) {
            productDao.deleteInvestorProduct()
        }

    override suspend fun getInvestorProductFromDb(): InvestorProduct = withContext(coroutineContext) {
        return@withContext productDao.getInvestorProduct()
    }
}