package com.asap.domain.repository

import com.asap.domain.entity.remote.auth.AuthResponse
import com.asap.domain.entity.remote.auth.RefreshTokenResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun authKakao(kakaoAccessToken: String): Flow<AuthResponse?>

    suspend fun registerToken()

    suspend fun cacheKakaoAuth(response: AuthResponse)

    suspend fun checkCachedAuth(): Boolean

    suspend fun refreshToken(): Flow<RefreshTokenResponse?>

    suspend fun updateToken(accessToken: String, refreshToken: String)
}