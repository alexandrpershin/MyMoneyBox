package com.example.minimoneybox.ui.splash

import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.R
import com.example.minimoneybox.preferences.SecuredSharedPreferences
import com.example.minimoneybox.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SplashViewModel(
    private val preferences: SecuredSharedPreferences,
    private val coroutineContext: CoroutineContext = Dispatchers.Main
) : BaseViewModel() {

    init {
        goNext()
    }

    private fun goNext() {
        viewModelScope.launch(coroutineContext) {
            delay(SPLASH_TIME_DELAY)

            val direction =
                if (preferences.isLoggedIn) {
                    SplashScreenFragmentDirections.fromSplashFragmentToUserAccountsFragment()
                } else {
                    SplashScreenFragmentDirections.fromSplashFragmentToLoginFragment()
                }

            goFirstScreen(direction, R.id.splashFragment)
        }
    }

    companion object {
        private const val SPLASH_TIME_DELAY = 2500L
    }

}

