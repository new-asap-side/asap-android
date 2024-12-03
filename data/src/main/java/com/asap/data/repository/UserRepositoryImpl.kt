package com.asap.data.repository

import android.util.Log
import com.asap.data.local.AppDatabase
import com.asap.data.remote.datasource.UserRemoteDataSource
import com.asap.data.remote.firebase.FCMTokenManager
import com.asap.domain.entity.ResultCard
import com.asap.domain.entity.local.User
import com.asap.domain.entity.remote.Alarm
import com.asap.domain.entity.remote.AuthKakaoResponse
import com.asap.domain.repository.UserRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: AppDatabase
) : UserRepository {
    override suspend fun authKakao(kakaoAccessToken: String): Flow<AuthKakaoResponse?> {
        return remoteDataSource.authKakao(kakaoAccessToken)
    }

    override suspend fun isCached(): Boolean {
        val userDao = localDataSource.userDao()
        return userDao.isCached()
    }

    override suspend fun cacheKakaoUserInfo(response: AuthKakaoResponse) {
        val userDao = localDataSource.userDao()
        userDao.insert(
            User(
                userId = response.userId,
                kakaoId = response.kakaoId,
                accessToken = response.accessToken,
                refreshToken = response.refreshToken,
            )
        )
    }

    override suspend fun getUserInfo(): User {
        val dao = localDataSource.userDao()
        return dao.selectAll().first()
    }

    override suspend fun fetchResultCardData(): Flow<ResultCard?> =
        remoteDataSource.resultCard

    override suspend fun fetchFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                FCMTokenManager.token = task.result
            }
        )
    }

    override suspend fun fetchUserAlarmList(): Flow<List<Alarm>?> =
        remoteDataSource.fetchAlarmList()

    companion object {
        private const val TAG = "UserRepositoryImpl"
    }
}