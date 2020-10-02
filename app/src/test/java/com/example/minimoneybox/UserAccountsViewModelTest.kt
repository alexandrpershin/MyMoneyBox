package com.example.minimoneybox

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.minimoneybox.api.ErrorType
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.response.InvestorProductsResponse
import com.example.minimoneybox.api.response.toInvestorProductModel
import com.example.minimoneybox.repository.AuthRepository
import com.example.minimoneybox.repository.InvestorProductsRepository
import com.example.minimoneybox.ui.useraccounts.UserAccountsViewModel
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserAccountsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testCoroutineRule = CoroutineTestRule()

    private lateinit var viewModel: UserAccountsViewModel

    @Mock
    private lateinit var errorObserver: Observer<ErrorType>

    @Mock
    private lateinit var loadingObserver: Observer<Boolean>

    @Mock
    private lateinit var authRepository: AuthRepository

    @Mock
    private lateinit var productsRepository: InvestorProductsRepository

    private val investorProduct = InvestorProductsResponse()

    private val successResponse = TaskResult.SuccessResult(investorProduct)

    private val errorResponse =
        TaskResult.ErrorResult(ErrorType.InternetError(R.string.message_internet_error))


    @Before
    fun initDependencies() {
        MockitoAnnotations.initMocks(this)

        testCoroutineRule.runBlockingTest {
            Mockito.`when`(productsRepository.getProductsFromServer())
                .thenReturn(successResponse)
        }

        viewModel = UserAccountsViewModel(
            productsRepository,
            authRepository,
            TestCoroutineDispatcher()
        )

        viewModel.errorNotifier.observeForever(errorObserver)
        viewModel.loadingState.observeForever(loadingObserver)
    }

    @Test
    fun test_show_loading_on_init() {
        val expectedValue = true

        val captor = ArgumentCaptor.forClass(Boolean::class.java)
        captor.run {
            verify(loadingObserver, Mockito.times(1)).onChanged(capture())
            Assert.assertEquals(expectedValue, value)
        }
    }

    @Test
    fun test_hide_loading_on_success() {
        val expectedValue = false

        val captor = ArgumentCaptor.forClass(Boolean::class.java)
        captor.run {
            verify(loadingObserver, Mockito.times(1)).onChanged(capture())
            Assert.assertEquals(expectedValue, value)
        }
    }

    @Test
    fun test_save_products_to_db() {
        testCoroutineRule.runBlockingTest {
            verify(
                productsRepository,
                Mockito.times(1)
            ).saveInvestorProductToDb(investorProduct.toInvestorProductModel())
        }
    }

    @Test
    fun test_failure() {
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(productsRepository.getProductsFromServer())
                .thenReturn(errorResponse)
        }

        viewModel.fetchInvestorProductsFromServer()

        val captor = ArgumentCaptor.forClass(ErrorType::class.java)
        captor.run {
            verify(errorObserver, Mockito.times(1)).onChanged(capture())
            Assert.assertEquals(errorResponse.errorType, value)
        }
    }


}