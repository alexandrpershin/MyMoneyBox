package com.example.minimoneybox.app

import com.example.minimoneybox.preferences.SecuredSharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module


val appModule: Module = module {

    //SecuredSharedPreferences
    single<SecuredSharedPreferences> { SecuredSharedPreferences(androidApplication()) }

}