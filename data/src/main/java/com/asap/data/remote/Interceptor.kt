package com.asap.data.remote

import android.util.Log
import com.asap.domain.entity.remote.auth.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = TokenManager.accessToken
        Log.v(TAG, "Header authorization access-token: $accessToken")

        return chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader(AUTH_KEY, "Bearer $accessToken").run {
                chain.proceed(build())
            }
    }

    companion object {
        const val AUTH_KEY = "Authorization"
        const val TAG = "HeeaderInterceptor"
        const val RETRY_COUNT = "Retry-count"
    }
}