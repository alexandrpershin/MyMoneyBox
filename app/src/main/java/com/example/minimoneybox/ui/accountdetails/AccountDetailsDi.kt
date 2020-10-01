package com.example.minimoneybox.ui.accountdetails

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AccountDetailsDi {
    fun getModule(): Module {
        return module {
            viewModel { (accountDetailsId: Int) ->
                AccountDetailsViewModel(
                    accountDetailsId,
                    get(),
                    get()
                )
            }
        }
    }

}