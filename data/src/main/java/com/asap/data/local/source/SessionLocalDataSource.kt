package com.asap.data.local.source

interface SessionLocalDataSource {
    suspend fun getAccessToken(): String?

    suspend fun getRefreshToken(): String?

    suspend fun getFCMToken(): String?

    suspend fun registerFCMToken(token: String)

    suspend fun updateAccessToken(accessToken: String)

    suspend fun updateRefreshToken(refreshToken: String)

    suspend fun clear()
}