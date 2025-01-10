package com.asap.data.remote

import android.util.Log
import com.asap.data.local.source.SessionLocalDataSource
import com.asap.domain.usecase.auth.RefreshTokenUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val sessionLocalDataSource: SessionLocalDataSource
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val tag = "Authenticator"
        Log.i(tag, "Access token is expired ...")
        return try {
            Log.i("Authenticator", "Retry with refresh token ..")
            val isSuccess = runBlocking { refreshTokenUseCase() }
            if (isSuccess) {
                response.request.newBuilder().removeHeader(HeaderInterceptor.AUTH_KEY).apply {
                    val accessToken = runBlocking { sessionLocalDataSource.getAccessToken() }
                    addHeader(HeaderInterceptor.AUTH_KEY, "Bearer $accessToken")
                }.build()
            } else null
        } catch (e: Exception) {
            Log.d(tag, "$e")
            null
        }
    }

}