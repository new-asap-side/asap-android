package com.asap.domain.repository

import android.content.Context
import com.asap.domain.entity.remote.auth.AuthResponse
import com.asap.domain.entity.remote.auth.RefreshTokenResponse
import com.kakao.sdk.auth.model.OAuthToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun kakaoLogin(
        scope: CoroutineScope,
        context: Context,
        callback: (OAuthToken?, Throwable?) -> Unit
    )

    suspend fun authKakao(kakaoAccessToken: String): Flow<AuthResponse?>

    suspend fun registerToken()

    suspend fun cacheKakaoAuth(response: AuthResponse)

    suspend fun checkCachedAuth(): Boolean

    suspend fun refreshToken(): Boolean

    suspend fun patchAlarmToken(token: String): Boolean

    suspend fun updateToken(accessToken: String, refreshToken: String)
}