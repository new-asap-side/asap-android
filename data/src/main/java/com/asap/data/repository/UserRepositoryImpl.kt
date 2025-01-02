package com.asap.data.repository

import android.util.Log
import com.asap.data.local.AppDatabase
import com.asap.data.local.source.SessionLocalDataSource
import com.asap.data.remote.datasource.UserRemoteDataSource
import com.asap.data.remote.firebase.FCMTokenManager
import com.asap.domain.entity.ResultCard
import com.asap.domain.entity.local.User
import com.asap.domain.repository.UserRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: AppDatabase,
    private val sessionLocalDataSource: SessionLocalDataSource
) : UserRepository {
    override suspend fun isCached(): Boolean {
        val dao = localDataSource.userDao()
        return dao.isCached()
    }

    override suspend fun getUserInfo(): User? {
        val dao = localDataSource.userDao()
        val selected = dao.selectAll()
        return if (selected.isEmpty()) null else selected.first()
    }

    override suspend fun fetchResultCardData(): Flow<ResultCard?> =
        remoteDataSource.fetchResultCard()

    override suspend fun checkNickname(nickname: String): Boolean? {
        return remoteDataSource.checkNickname(nickname)?.isPossible
    }

    override suspend fun saveProfile(
        userId: Int,
        nickname: String,
        profileImg: String
    ) {
        // profileImg를 DB에 저장시키면
        remoteDataSource.saveProfile(userId, nickname, profileImg)
            .also {
                localDataSource.userDao().apply {
                    updateProfileImg(it?.profileImageUrl, userId)
                    updateNickname(nickname, userId)
                }
            }
    }

    override suspend fun fetchFCMToken() {
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

    override suspend fun deleteRemoteUserInfo(survey: String): Flow<Unit> =
        remoteDataSource.deleteUser(survey = survey)

    override suspend fun deleteLocalUserInfo() {
        // Room DB delete
        val userDao = localDataSource.userDao()
        userDao.deleteAll()

        // session data store clear
        sessionLocalDataSource.clear()
    }

    companion object {
        private const val TAG = "UserRepositoryImpl"
    }
}