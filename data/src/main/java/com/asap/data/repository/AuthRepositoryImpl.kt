package com.asap.data.repository

import com.asap.data.local.AppDatabase
import com.asap.data.local.source.SessionLocalDataSource
import com.asap.data.remote.datasource.AuthRemoteDataSource
import com.asap.domain.entity.local.User
import com.asap.domain.entity.remote.auth.AuthResponse
import com.asap.domain.entity.remote.auth.TokenManager
import com.asap.domain.repository.AuthRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
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

    override suspend fun registerToken() {
        with (sessionLocalDataSource) {
            val fcmToken = getFCMToken()
            println("FCM-token: $fcmToken")
            if (fcmToken == null) {
                FirebaseMessaging.getInstance().token.addOnCompleteListener(
                    OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            return@OnCompleteListener
                        }

                        // memory cache
                        TokenManager.fcmToken = task.result.also(::println)
                        CoroutineScope(Dispatchers.Default).launch {
                            // local cache
                            sessionLocalDataSource.registerFCMToken(task.result)
                        }
                    }
                )
            } else {
                TokenManager.fcmToken = fcmToken
            }

            TokenManager.run {
                accessToken = getAccessToken()
                refreshToken = getRefreshToken()
            }
        }
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

        sessionLocalDataSource.run {
            updateAccessToken(response.accessToken)
            updateRefreshToken(response.refreshToken)
        }
    }

    override suspend fun checkCachedAuth(): Boolean {
        return userDao.isCached()
    }

    override suspend fun refreshToken(): Boolean {
        try {
            val refreshToken = sessionLocalDataSource.getRefreshToken() ?: ""
            remoteDataSource.refreshToken(token = refreshToken)?.let { (access, refresh) ->
                updateToken(access, refresh)

                TokenManager.run {
                    this.accessToken = access
                    this.refreshToken = refresh
                }
            }
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