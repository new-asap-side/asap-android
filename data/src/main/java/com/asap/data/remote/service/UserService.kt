package com.asap.data.remote.service

import com.asap.data.remote.request.SaveProfileRequest
import com.asap.data.remote.response.CheckNicknameResponse
import com.asap.data.remote.response.SaveProfileResponse
import com.asap.domain.entity.ResultCard
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface UserService {
    @GET("/")
    suspend fun fetchResultCard(): Response<ResultCard>

    @POST("/profile/check-nick-name")
    suspend fun checkNickname(@Body body: Map<String, String>): Response<CheckNicknameResponse>

    @POST("/profile/save-profile")
    suspend fun saveProfile(@Body body: SaveProfileRequest): Response<SaveProfileResponse>

    @HTTP(method = "DELETE", path = "/auth/user", hasBody = true)
    suspend fun deleteUser(@Body body: Map<String, String>): Response<Unit>

}

