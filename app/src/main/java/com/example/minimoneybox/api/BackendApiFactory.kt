package com.example.minimoneybox.api


import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.minimoneybox.BuildConfig
import com.example.minimoneybox.preferences.SecuredSharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BackendApiFactory {

    private val gson: Gson = GsonBuilder().create()

    private val gsonFactory: GsonConverterFactory = GsonConverterFactory.create(gson)

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        // print in console all request/response
        val consoleInterceptor = HttpLoggingInterceptor().also {
            it.level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }

        return consoleInterceptor
    }

    private fun provideHeaderInterceptor(securedPreferences: SecuredSharedPreferences): HeaderInterceptor {
        return HeaderInterceptor(securedPreferences)
    }

    fun provideRetrofit(application: Context, securedPreferences: SecuredSharedPreferences): Retrofit {
        val retrofit =
            Retrofit.Builder()
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(provideOkHttpClient(application, securedPreferences))
                .baseUrl(BASE_URL)
                .build()

        return retrofit
    }

    private fun provideOkHttpClient(
        application: Context,
        securedPreferences: SecuredSharedPreferences
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(150, TimeUnit.SECONDS)
            .readTimeout(150, TimeUnit.SECONDS)
            .addInterceptor(provideHeaderInterceptor(securedPreferences))
            .addInterceptor(ChuckerInterceptor(application))
            .addInterceptor(provideHttpLoggingInterceptor())
            .build()
    }

    companion object {
        private const val BASE_URL = "https://api-test01.moneyboxapp.com/"
    }

}

inline fun <reified T> createApiService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}