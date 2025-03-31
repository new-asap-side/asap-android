package com.asap.data.remote

import com.asap.domain.ExpiredTokenHandler
import com.asap.domain.entity.remote.auth.TokenManager
import com.asap.domain.usecase.auth.RefreshTokenUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val refreshTokenUseCase: RefreshTokenUseCase
) : Authenticator {
    private val maxRetryCount = 3

    @Synchronized
    override fun authenticate(route: Route?, response: Response): Request? {
        return try {
            // 재시도 횟수 제한
            val retryCount = response.header(HeaderInterceptor.RETRY_COUNT)?.toIntOrNull() ?: 0
            println("Unauthorized request ($retryCount)")
            if (retryCount >= maxRetryCount) {
                return null
            }

            val isSuccess = runBlocking {
                refreshTokenUseCase()
            }
            if (isSuccess) {
                response.request.newBuilder().apply {
                    header(HeaderInterceptor.AUTH_KEY, "Bearer ${TokenManager.accessToken}")
                    header(HeaderInterceptor.RETRY_COUNT, "${retryCount + 1}")
                }.build()
            } else {
                expired()
                null
            }
        } catch (e: Exception) {
            expired()
            null
        }
    }

    private fun expired() {
        ExpiredTokenHandler.emit(CoroutineScope(Dispatchers.Default))
    }
}