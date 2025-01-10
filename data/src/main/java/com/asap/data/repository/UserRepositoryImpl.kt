package com.asap.data.repository

import com.asap.data.local.AppDatabase
import com.asap.data.local.source.SessionLocalDataSource
import com.asap.data.remote.datasource.UserRemoteDataSource
import com.asap.domain.entity.local.User
import com.asap.domain.entity.remote.WhetherResponse
import com.asap.domain.entity.remote.user.UserProfile
import com.asap.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    localDataSource: AppDatabase,
    private val sessionLocalDataSource: SessionLocalDataSource
) : UserRepository {
    private val userDao = localDataSource.userDao()

    override suspend fun isCached(): Boolean {
        return userDao.isCached()
    }

    override suspend fun getUserInfo(): User? {
        return userDao.selectAll().firstOrNull()
    }

    override suspend fun fetchUserProfile(): Flow<UserProfile?> {
        return remoteDataSource.fetchUserProfile(userId = getUserId().toString())
    }

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
                userDao.run {
                    updateProfileImg(it?.profileImageUrl, userId)
                    updateNickname(nickname, userId)
                }
            }
    }

    override suspend fun deleteRemoteUserInfo(survey: String): Flow<WhetherResponse?> {
        return remoteDataSource.deleteUser(userId = getUserId(), survey = survey)
    }

    override suspend fun deleteLocalUserInfo() {
        // Room DB delete
        userDao.deleteAll()

        // session data store clear
        sessionLocalDataSource.clear()
    }

    private suspend fun getUserId(): Int {
        return (userDao.selectAll().firstOrNull()?.userId ?: "-1").toInt()
    }

    companion object {
        private const val TAG = "UserRepositoryImpl"
    }
}