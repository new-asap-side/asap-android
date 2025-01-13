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
        val accessToken = runBlocking {
            sessionLocalDataSource.getAccessToken() ?: ""
        }
        Log.v(TAG, "accesssToken: $accessToken")

        val requestBuilder = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader(AUTH_KEY, "Bearer $accessToken")

        return chain.proceed(requestBuilder.build())
    }

    companion object {
        const val AUTH_KEY = "Authorization"
        const val TAG = "HeeaderInterceptor"
    }
}