package com.asap.data.remote.datasource

import com.asap.data.remote.request.SaveProfileRequest
import com.asap.data.remote.response.CheckNicknameResponse
import com.asap.data.remote.response.SaveProfileResponse
import com.asap.data.remote.service.UserService
import com.asap.domain.entity.ResultCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun fetchResultCard(): Flow<ResultCard?> = flow {
        val response = userService.fetchResultCard()

        if (!response.isSuccessful) {
            throw HttpException(response)
        }
        emit(response.body())
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

    suspend fun deleteUser(survey: String): Flow<Unit> = flow {
        val response = userService.deleteUser(
            hashMapOf("survey" to survey)
        )
        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        emit(Unit)
    }
}