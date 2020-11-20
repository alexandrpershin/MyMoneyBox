package com.example.minimoneybox.api

import com.example.minimoneybox.BuildConfig
import com.example.minimoneybox.utils.MockResponse
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody


/**
 * Interceptor which allows to intercept HTTP requests and return different mock responses.
* */

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url.toUri().toString()
            val responseString = when {
                uri.endsWith("login/") -> MockResponse.loginResponse
                uri.endsWith("investorproducts/") -> MockResponse.investorProductsResponse
                uri.endsWith("oneoffpayments/") -> MockResponse.productPaymentResponse()

                else -> throw UnsupportedOperationException("Illegal mock path provided")
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(responseString.toByteArray().toResponseBody("application/json".toMediaType()))
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }

}