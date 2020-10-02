package com.example.minimoneybox

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.minimoneybox.api.ErrorType
import com.example.minimoneybox.api.TaskResult
import com.example.minimoneybox.api.response.LoginResponse
import com.example.minimoneybox.preferences.SecuredSharedPreferences
import com.example.minimoneybox.repository.AuthRepository
import com.example.minimoneybox.repository.InvestorProductsRepository
import com.example.minimoneybox.ui.login.LoginViewModel
import com.example.minimoneybox.utils.EmailValidator
import com.nhaarman.mockitokotlin2.doReturn
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

class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testCoroutineRule = CoroutineTestRule()

    private lateinit var viewModel: LoginViewModel

    @Mock
    private lateinit var errorInputObserver: Observer<ErrorType>

    @Mock
    private lateinit var loadingObserver: Observer<Boolean>

    @Mock
    private lateinit var authRepository: AuthRepository

    @Mock
    private lateinit var productsRepository: InvestorProductsRepository

    @Mock
    private lateinit var emailValidator: EmailValidator

    @Mock
    private lateinit var securedPreferences: SecuredSharedPreferences

    @Before
    fun initDependencies() {
        MockitoAnnotations.initMocks(this)

        viewModel = LoginViewModel(
            securedPreferences,
            authRepository,
            productsRepository,
            emailValidator,
            TestCoroutineDispatcher()
        )

        viewModel.errorNotifier.observeForever(errorInputObserver)
        viewModel.loadingState.observeForever(loadingObserver)
    }

    @Test
    fun test_login_empty_email() {
        val expectedValue = ErrorType.InputError(R.string.error_empty_email)

        val email = ""
        val password = "SuperPassword"
        val userName = "Alex"

        viewModel.logIn(email, password, userName)


        val captor = ArgumentCaptor.forClass(ErrorType::class.java)
        captor.run {
            verify(errorInputObserver, Mockito.times(1)).onChanged(capture())
            Assert.assertEquals(expectedValue, value)
        }
    }

    @Test
    fun test_login_empty_password() {
        val expectedValue = ErrorType.InputError(R.string.error_empty_password)

        val email = "SuperEmail"
        val password = ""
        val userName = "Alex"

        Mockito.`when`(emailValidator.isValid(email)).doReturn(true)

        viewModel.logIn(email, password, userName)

        val captor = ArgumentCaptor.forClass(ErrorType::class.java)
        captor.run {
            verify(errorInputObserver, Mockito.times(1)).onChanged(capture())
            Assert.assertEquals(expectedValue, value)
        }
    }

    @Test
    fun test_login_success() {
        val loginResponse = LoginResponse()

        val expectedValue = TaskResult.SuccessResult<LoginResponse>(loginResponse)

        val email = "jaeren+androidtest@moneyboxapp.com"
        val password = "super pasword"
        val userName = "Alex"

        Mockito.`when`(emailValidator.isValid(email)).doReturn(true)

        viewModel.logIn(email, password, userName)

        Mockito.verifyNoMoreInteractions(errorInputObserver);
    }

    @Test
    fun test_loading_on_login() {
        val expectedValue = true

        val email = "jaeren+androidtest@moneyboxapp.com"
        val password = "super pasword"
        val userName = "Alex"

        Mockito.`when`(emailValidator.isValid(email)).doReturn(true)

        viewModel.logIn(email, password, userName)

        testCoroutineRule.runBlockingTest {
            val captor = ArgumentCaptor.forClass(Boolean::class.java)
            captor.run {
                verify(loadingObserver, Mockito.times(1)).onChanged(capture())
                Assert.assertEquals(expectedValue, value)
            }
        }
    }

}