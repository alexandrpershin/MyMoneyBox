package com.example.minimoneybox.app

import android.app.Application
import com.example.minimoneybox.ui.splash.SplashDi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoneyboxApplication : Application() {

    private val modules = listOf(
        appModule,
        SplashDi.getModule()
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