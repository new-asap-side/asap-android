package com.asap.data.remote.datasource

import com.asap.data.remote.request.SaveProfileRequest
import com.asap.data.remote.response.CheckNicknameResponse
import com.asap.data.remote.response.FetchProfileItemResponse
import com.asap.data.remote.service.UserService
import com.asap.domain.entity.remote.DeleteUserRequestBody
import com.asap.domain.entity.remote.WhetherResponse
import com.asap.domain.entity.remote.user.SaveProfileResponse
import com.asap.domain.entity.remote.user.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
) {
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
        val response = userService.saveProfile(request)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        return response.body()
    }

    suspend fun fetchUserProfile(userId: String): UserProfile? {
        val response = userService.fetchUserProfile(userId)
        if(!response.isSuccessful) {
            throw HttpException(response)
        }

        return response.body()
    }

    suspend fun deleteUser(userId: Int, survey: String): Flow<WhetherResponse?> = flow {
        userService.deleteUser(
            DeleteUserRequestBody(
                userId = userId,
                userLeaveReason = survey
            )
        ).also { response ->
            if (!response.isSuccessful) throw HttpException(response)
            emit(response.body())
        }
    }

    suspend fun fetchProfileItem(userId: String): FetchProfileItemResponse {
        val response = userService.fetchProfileItem(userId)

        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        return response.body() ?: throw Exception("Fetch Profile Item Body Is Null")
    }

    suspend fun unlockProfileItem(profileId: Int): Boolean {
        val response = userService.unlockProfileItem(
            mapOf("profile_id" to profileId)
        )

        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        return response.body()?.result ?: false
    }

    suspend fun saveProfileItem(profileId: Int): Boolean {
        val response = userService.saveProfileItem(
            mapOf("profile_id" to profileId)
        )

        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        return response.body()?.result ?: false
    }
}