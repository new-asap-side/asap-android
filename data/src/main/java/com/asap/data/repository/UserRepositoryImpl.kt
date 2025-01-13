package com.asap.data.repository

import android.util.Log
import com.asap.data.local.AppDatabase
import com.asap.data.local.source.SessionLocalDataSource
import com.asap.data.remote.datasource.UserRemoteDataSource
import com.asap.domain.entity.local.User
import com.asap.domain.entity.remote.WhetherResponse
import com.asap.domain.entity.remote.user.SaveProfileResponse
import com.asap.domain.entity.remote.user.UserProfile
import com.asap.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
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
    ): Boolean {
        return remoteDataSource.saveProfile(userId, nickname, profileImg).let { response ->
            if (response?.result == true) {
                Log.d("UserRepository", "Room DB update [${response.profileImageUrl}, $nickname]")
                userDao.updateProfileImg(response.profileImageUrl ?: "", userId = userId)
                userDao.updateNickname(nickname = nickname, userId = userId)
            }

            response?.result ?: false
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