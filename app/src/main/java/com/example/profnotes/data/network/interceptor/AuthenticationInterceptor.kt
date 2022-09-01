package com.example.profnotes.data.network.interceptor

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor:Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val credentials = Credentials
            .basic("admin","admin")

        val newRequest = request
            .newBuilder()
            .addHeader(name = "Authentication", value = "Basic $credentials")
            .build()

        return chain.proceed(newRequest)
    }
}