package com.asap.data.remote

import android.util.Log
import com.asap.data.local.source.SessionLocalDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val sessionLocalDataSource: SessionLocalDataSource
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestPath = chain.request().url.encodedPath
        val requestBuilder = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")

        if (!requestPath.startsWith("/auth")) {
            var accessToken: String
            runBlocking { accessToken = sessionLocalDataSource.getAccessToken() ?: "" }
            requestBuilder.addHeader("Authorization", "Bearer $accessToken")
        }
        Log.d("HeaderInterceptor:","${requestBuilder.build()}")

        return chain.proceed(requestBuilder.build())
    }
}