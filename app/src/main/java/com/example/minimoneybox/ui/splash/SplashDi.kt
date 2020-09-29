package com.example.minimoneybox.ui.splash

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object SplashDi {
    fun getModule(): Module {
        return module {
            viewModel { SplashViewModel(get()) }
        }
    }

}