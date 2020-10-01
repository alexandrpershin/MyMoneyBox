package com.example.minimoneybox.app

import com.example.minimoneybox.api.BackendApiFactory
import com.example.minimoneybox.api.createApiService
import com.example.minimoneybox.persistence.LocalDatabase
import com.example.minimoneybox.preferences.SecuredSharedPreferences
import com.example.minimoneybox.repository.AuthRepository
import com.example.minimoneybox.repository.InvestorProductsRepository
import com.example.minimoneybox.repository.repositoryImpl.AuthRepositoryImpl
import com.example.minimoneybox.repository.repositoryImpl.InvestorProductsRepositoryImpl
import com.example.minimoneybox.service.AuthService
import com.example.minimoneybox.service.InvestorProductsService
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit


val appModule: Module = module {

    //SecuredSharedPreferences
    single<SecuredSharedPreferences> { SecuredSharedPreferences(androidApplication()) }

    //Retrofit
    single<Retrofit> { BackendApiFactory().provideRetrofit(get(), get()) }

    //Local database
    single<LocalDatabase> { LocalDatabase.getInstance(androidApplication()) }

    //Repository
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<InvestorProductsRepository> { InvestorProductsRepositoryImpl(get()) }

    //API service
    single<AuthService> { createApiService<AuthService>(get()) }
    single<InvestorProductsService> { createApiService<InvestorProductsService>(get()) }
}