package com.example.minimoneybox.app

import android.app.Application
import com.example.minimoneybox.ui.login.LoginDi
import com.example.minimoneybox.ui.splash.SplashDi
import com.example.minimoneybox.ui.useraccounts.UserAccountsDi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoneyboxApplication : Application() {

    private val modules = listOf(
        appModule,
        SplashDi.getModule(),
        LoginDi.getModule(),
        UserAccountsDi.getModule()
    )

    override fun onCreate() {
        super.onCreate()

        instance = this

        startKoin {
            androidLogger()
            androidContext(this@MoneyboxApplication)
            modules(modules)
        }
    }

    companion object {
        lateinit var instance: MoneyboxApplication
    }

}