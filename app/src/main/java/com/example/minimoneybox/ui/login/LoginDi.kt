package com.example.minimoneybox.ui.login

import com.example.minimoneybox.utils.EmailValidatorImp
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object LoginDi {
    fun getModule(): Module {
        return module {
            viewModel { LoginViewModel(get(), get(), get(), EmailValidatorImp()) }
        }
    }

}