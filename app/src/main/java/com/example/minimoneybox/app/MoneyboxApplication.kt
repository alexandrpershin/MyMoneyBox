package com.example.minimoneybox.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoneyboxApplication : Application() {

    private val modules = listOf(
        appModule
    )

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MoneyboxApplication)
            modules(modules)
        }
    }


}