package com.asap.data.repository

import android.util.Log
import com.asap.data.local.AppDatabase
import com.asap.data.local.source.SessionLocalDataSource
import com.asap.data.mapper.ProfileItemListMapper
import com.asap.data.remote.datasource.UserRemoteDataSource
import com.asap.domain.entity.local.User
import com.asap.domain.entity.remote.WhetherResponse
import com.asap.domain.entity.remote.user.SaveProfileResponse
import com.asap.domain.entity.remote.user.UserProfile
import com.asap.domain.model.ProfileItemListModel
import com.asap.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
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

    override suspend fun fetchUserProfile(): UserProfile? {
        try {
            val userId = getUserId()

            val response = remoteDataSource.fetchUserProfile(userId = getUserId().toString())
            if (response != null) {
                userDao.updateNickname(userId = userId, nickname = response.nickName)
                userDao.updateProfileImg(userId = userId, profileImg = response.profileImageUrl)
            }

            return response
        } catch (e: HttpException) {
            return null
        }
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
            Log.d(TAG, "$response")
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

    override suspend fun fetchProfileItem(userId: String): ProfileItemListModel {
        return remoteDataSource.fetchProfileItem(userId).let {
            ProfileItemListMapper.toDomain(it)
        }
    }

    override suspend fun unlockProfileItem(profileId: Int): Boolean {
        return remoteDataSource.unlockProfileItem(profileId)
    }

    override suspend fun saveProfileItem(profileItemId: Int, profileItemName: String, userId: Int, resetFlag: Boolean): Boolean {
        return remoteDataSource.saveProfileItem(profileItemId).also { response ->
            if (response) {
              userDao.updateProfileItem(profileItem = if (resetFlag) null else profileItemName, userId = userId)
            }
        }
    }

    companion object {
        private const val TAG = "UserRepositoryImpl"
    }
}