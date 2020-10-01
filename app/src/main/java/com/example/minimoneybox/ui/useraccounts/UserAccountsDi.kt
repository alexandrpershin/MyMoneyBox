package com.example.minimoneybox.ui.useraccounts

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object UserAccountsDi {
    fun getModule(): Module {
        return module {
            viewModel { UserAccountsViewModel(get(), get()) }
        }
    }

}