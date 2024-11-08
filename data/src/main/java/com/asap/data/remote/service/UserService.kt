package com.asap.data.remote.service

import com.asap.data.remote.request.SaveProfileRequest
import com.asap.data.remote.response.CheckNicknameResponse
import com.asap.data.remote.response.SaveProfileResponse
import com.asap.domain.entity.ResultCard
import com.asap.domain.entity.remote.AuthKakaoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("auth/kakao")
    suspend fun authKakao(@Body body: Map<String, String>): Response<AuthKakaoResponse?>

    @GET("/")
    suspend fun fetchResultCard(): Response<ResultCard>

    @POST("/profile/check-nick-name")
    suspend fun checkNickname(@Body body: Map<String, String>): Response<CheckNicknameResponse>

    @POST("/profile/save-profile")
    suspend fun saveProfile(@Body body: SaveProfileRequest): Response<SaveProfileResponse>
}

