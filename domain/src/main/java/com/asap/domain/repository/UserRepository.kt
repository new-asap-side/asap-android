package com.asap.domain.repository

import com.asap.domain.entity.ResultCard
import com.asap.domain.entity.local.User
import com.asap.domain.entity.remote.Alarm
import com.asap.domain.entity.remote.AuthKakaoResponse
import com.kakao.sdk.auth.model.OAuthToken
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun authKakao(kakaoAccessToken: String): Flow<AuthKakaoResponse?>

    suspend fun isCached(): Boolean

    suspend fun cacheKakaoUserInfo(response: AuthKakaoResponse)

    suspend fun getUserInfo(): User

    suspend fun fetchResultCardData(): Flow<ResultCard?>

    suspend fun checkNickname(nickname: String): Boolean?

    suspend fun saveProfile(
        userId: Int,
        nickname: String,
        profileImg: String
    )

    suspend fun fetchFCMToken()

    suspend fun fetchUserAlarmList(): Flow<List<Alarm>?>

    suspend fun delteUserInfo(survey: String): Flow<Unit>

}