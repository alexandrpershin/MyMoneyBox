package com.example.minimoneybox.ui.splash

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.R
import com.example.minimoneybox.preferences.UserPreferences
import com.example.minimoneybox.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SplashViewModel(
    private val preferences: UserPreferences,
    private val coroutineContext: CoroutineContext = Dispatchers.Main
) : BaseViewModel() {

    init {
        goNext()
    }

    private fun goNext() {
        viewModelScope.launch(coroutineContext) {
            preferences.isLoggedIn.collect { loggedIn ->
                delay(SPLASH_TIME_DELAY)

                val direction =
                    if (loggedIn) {
                        SplashScreenFragmentDirections.fromSplashFragmentToUserAccountsFragment()
                    } else {
                        SplashScreenFragmentDirections.fromSplashFragmentToLoginFragment()
                    }

                goFirstScreen(direction, R.id.splashFragment)
            }
        }
    }

    companion object {
        private const val SPLASH_TIME_DELAY = 2500L
    }

}

