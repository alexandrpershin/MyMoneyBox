package com.example.minimoneybox.app

import com.example.minimoneybox.api.BackendApiFactory
import com.example.minimoneybox.api.createApiService
import com.example.minimoneybox.preferences.SecuredSharedPreferences
import com.example.minimoneybox.repository.AuthRepository
import com.example.minimoneybox.repository.repositoryImpl.AuthRepositoryImpl
import com.example.minimoneybox.service.AuthService
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit


val appModule: Module = module {

    //SecuredSharedPreferences
    single<SecuredSharedPreferences> { SecuredSharedPreferences(androidApplication()) }

    //Retrofit
    single<Retrofit> { BackendApiFactory().provideRetrofit(get(), get()) }

    //Repository
    single<AuthRepository> { AuthRepositoryImpl(get()) }

    //API service
    single<AuthService> { createApiService<AuthService>(get()) }
}