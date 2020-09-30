package com.example.minimoneybox.api


import com.example.minimoneybox.preferences.SecuredSharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor(private val preferences: SecuredSharedPreferences) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = preferences.token
        val builder = chain.request().newBuilder()

        if (token.isNotEmpty()) {
            builder.addHeader(KEY_AUTHORIZATION, "Bearer $token")
        }

        builder.addHeader(KEY_APP_ID, "3a97b932a9d449c981b595")
        builder.addHeader(KEY_CONTENT_TYPE, "application/json")
        builder.addHeader(KEY_APP_VERSION, "7.15.0")
        builder.addHeader(KEY_API_VERSION, "3.0.0")

        return chain.proceed(builder.build())
    }

    companion object {
        private const val KEY_AUTHORIZATION = "Authorization"
        private const val KEY_APP_ID = "AppId"
        private const val KEY_CONTENT_TYPE = "Content-Type"
        private const val KEY_API_VERSION = "apiVersion"
        private const val KEY_APP_VERSION = "appVersion"
    }
}