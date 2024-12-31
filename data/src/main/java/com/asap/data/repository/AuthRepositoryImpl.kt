package com.asap.data.repository

import com.asap.data.local.AppDatabase
import com.asap.data.local.source.SessionLocalDataSource
import com.asap.data.remote.datasource.AuthRemoteDataSource
import com.asap.domain.entity.local.User
import com.asap.domain.entity.remote.auth.AuthResponse
import com.asap.domain.entity.remote.auth.RefreshTokenResponse
import com.asap.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AppDatabase,
    private val sessionLocalDataSource: SessionLocalDataSource
): AuthRepository {
    override suspend fun authKakao(kakaoAccessToken: String): Flow<AuthResponse?> {
        return remoteDataSource.authKakao(kakaoAccessToken = kakaoAccessToken)
    }

    override suspend fun cacheKakaoAuth(response: AuthResponse) {
        val userDao = localDataSource.userDao()
        userDao.insert(
            User(
                userId = response.userId,
                kakaoId = response.socialLoginId,
                accessToken = response.accessToken,
                refreshToken = response.refreshToken,
            )
        )

        sessionLocalDataSource.updateAccessToken(response.accessToken)
        sessionLocalDataSource.updateAccessToken(response.refreshToken)
    }

    override suspend fun refreshToken(): Flow<RefreshTokenResponse?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateToken(accessToken: String, refreshToken: String) {
        TODO("Not yet implemented")
    }
}