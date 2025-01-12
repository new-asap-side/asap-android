package com.asap.domain.repository

import com.asap.domain.entity.local.User
import com.asap.domain.entity.remote.WhetherResponse
import com.asap.domain.entity.remote.user.SaveProfileResponse
import com.asap.domain.entity.remote.user.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun isCached(): Boolean

    suspend fun getUserInfo(): User?

    suspend fun fetchUserProfile(): Flow<UserProfile?>

    suspend fun checkNickname(nickname: String): Boolean?

    suspend fun saveProfile(
        userId: Int,
        nickname: String,
        profileImg: String
    ): SaveProfileResponse?

    suspend fun deleteRemoteUserInfo(survey: String): Flow<WhetherResponse?>

    suspend fun deleteLocalUserInfo()

}