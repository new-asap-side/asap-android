package com.asap.domain.repository

import com.asap.domain.entity.ResultCard
import com.asap.domain.entity.local.User
import com.asap.domain.entity.remote.WhetherResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun isCached(): Boolean

    suspend fun getUserInfo(): User?

    suspend fun fetchResultCardData(): Flow<ResultCard?>

    suspend fun checkNickname(nickname: String): Boolean?

    suspend fun saveProfile(
        userId: Int,
        nickname: String,
        profileImg: String
    )

    suspend fun fetchFCMToken()

    suspend fun deleteRemoteUserInfo(survey: String): Flow<WhetherResponse?>

    suspend fun deleteLocalUserInfo()

}