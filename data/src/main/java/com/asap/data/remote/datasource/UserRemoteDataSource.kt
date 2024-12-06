package com.asap.data.remote.datasource

import com.asap.data.remote.request.SaveProfileRequest
import com.asap.data.remote.response.CheckNicknameResponse
import com.asap.data.remote.response.SaveProfileResponse
import com.asap.data.remote.service.UserService
import com.asap.domain.entity.ResultCard
import com.asap.domain.entity.remote.Alarm
import com.asap.domain.entity.remote.AuthKakaoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
) {
    val resultCard: Flow<ResultCard?> = flow {
        val response = userService.fetchResultCard()
        emit(response.body())
    }

    suspend fun authKakao(kakaoAccessToken: String): Flow<AuthKakaoResponse?> = flow {
        val response = userService.authKakao(
            hashMapOf("kakaoAccessToken" to kakaoAccessToken)
        )
        emit(response.body())
    }

    suspend fun fetchAlarmList(): Flow<List<Alarm>> = flow {
        val response = userService.fetchAlarmList()
//        if(!response.isSuccessful) {
//            throw HttpException(response)
//        }
        emit(response.body() ?: listOf())
    }
    
    suspend fun checkNickname(nickname: String): CheckNicknameResponse? {
        return userService.checkNickname(
            hashMapOf("nickName" to nickname)
        ).body()
    }

    suspend fun saveProfile(
        userId: Int,
        nickname: String,
        profileImg: String
    ): SaveProfileResponse? {
        val request = SaveProfileRequest(
            userId = userId,
            nickname = nickname,
            profileImg = profileImg
        )

        return userService.saveProfile(request).body()
    }
}