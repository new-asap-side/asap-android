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
            val accessToken = runBlocking {
                refreshTokenUseCase()
                sessionLocalDataSource.getAccessToken()
            } ?: ""
            Log.i(tag, "Refresh access token: $accessToken")

            response.request.newBuilder().removeHeader(HeaderInterceptor.AUTH_KEY).apply {
                addHeader(HeaderInterceptor.AUTH_KEY, "Bearer $accessToken")
            }.build()
        } catch (e: Exception) {
            Log.e(tag, "$e")
            null
        }
    }

}