package com.asap.data.remote.datasource

import com.asap.data.remote.request.SaveProfileRequest
import com.asap.data.remote.response.CheckNicknameResponse
import com.asap.data.remote.response.SaveProfileResponse
import com.asap.data.remote.service.UserService
import com.asap.domain.entity.ResultCard
import com.asap.domain.entity.remote.DeleteUserRequestBody
import com.asap.domain.entity.remote.WhetherResponse
import com.asap.domain.entity.remote.user.UserProfile
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

    suspend fun fetchUserProfile(userId: Int): Flow<UserProfile?> = flow {
        val response = userService.fetchUserProfile(userId)
        if(!response.isSuccessful) {
            throw HttpException(response)
        }

        emit(response.body())
    }

    suspend fun deleteUser(userId: Int, survey: String): Flow<WhetherResponse?> = flow {
        userService.deleteUser(
            DeleteUserRequestBody(
                userId = userId,
                userLeaveReason = survey
            )
        ).also { response ->
            if (response.isSuccessful) throw HttpException(response)
            emit(response.body())
        }
    }
}