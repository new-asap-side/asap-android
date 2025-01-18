package com.asap.data.repository

import android.util.Log
import com.asap.data.local.AppDatabase
import com.asap.data.local.source.SessionLocalDataSource
import com.asap.data.remote.datasource.AuthRemoteDataSource
import com.asap.data.remote.firebase.FCMTokenManager
import com.asap.domain.entity.local.User
import com.asap.domain.entity.remote.auth.AuthResponse
import com.asap.domain.repository.AuthRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val sessionLocalDataSource: SessionLocalDataSource,
    localDataSource: AppDatabase
) : AuthRepository {
    private val userDao = localDataSource.userDao()

    override suspend fun authKakao(kakaoAccessToken: String): Flow<AuthResponse?> {
        return remoteDataSource.authKakao(kakaoAccessToken = kakaoAccessToken)
    }

    /// FCM Token
    override suspend fun registerToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                FCMTokenManager.token = task.result
                Log.d(TAG, FCMTokenManager.token)
            }
        )
    }

    override suspend fun cacheKakaoAuth(response: AuthResponse) {
        userDao.insert(
            User(
                userId = response.userId,
                kakaoId = response.socialLoginId,
                accessToken = response.accessToken,
                refreshToken = response.refreshToken,
            )
        )

        sessionLocalDataSource.updateAccessToken(response.accessToken)
        sessionLocalDataSource.updateRefreshToken(response.refreshToken)
    }

    override suspend fun checkCachedAuth(): Boolean {
        return userDao.isCached()
    }

    override suspend fun refreshToken(): Boolean {
        try {
            val refreshToken = sessionLocalDataSource.getRefreshToken() ?: ""
            Log.v(TAG, "refresh token by sessionLocalDataSource: $refreshToken")

            val response = remoteDataSource.refreshToken(refreshToken)
            updateToken(
                response?.accessToken ?: "",
                response?.refreshToken ?: ""
            )
            return true
        } catch (e: HttpException) {
            return false
        }
    }

    override suspend fun updateToken(accessToken: String, refreshToken: String) {
        val userId = (userDao.selectAll().firstOrNull()?.userId ?: "-1").toInt()
        // Room update
        userDao.updateAccessToken(accessToken = accessToken, userId = userId)
        userDao.updateRefreshToken(refreshToken = refreshToken, userId = userId)

        // session update
        sessionLocalDataSource.updateAccessToken(accessToken)
        sessionLocalDataSource.updateRefreshToken(refreshToken)
    }

    companion object {
        const val TAG = "AuthRepositoryImpl"
    }
}