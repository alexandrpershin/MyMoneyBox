package com.example.minimoneybox.ui.splash

import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.preferences.SecuredSharedPreferences
import com.example.minimoneybox.ui.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val preferences: SecuredSharedPreferences,
    private val timeDelay: Long = SPLASH_TIME_DELAY
) : BaseViewModel() {

    init {
        goNext()
    }

    private fun goNext() {
        viewModelScope.launch {
            delay(timeDelay)

            val direction =
                if (preferences.isLoggedIn) {
                    SplashScreenFragmentDirections.splashFragmentToMainGraph()
                } else {
                    SplashScreenFragmentDirections.splashFragmentToAuthGraph()
                }

            goTo(direction)
        }
    }

    companion object {
        private const val SPLASH_TIME_DELAY = 2500L
    }

}

