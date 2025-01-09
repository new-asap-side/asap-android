package com.asap.data.remote.service

import com.asap.data.remote.request.SaveProfileRequest
import com.asap.data.remote.response.CheckNicknameResponse
import com.asap.data.remote.response.SaveProfileResponse
import com.asap.domain.entity.ResultCard
import com.asap.domain.entity.remote.DeleteUserRequestBody
import com.asap.domain.entity.remote.WhetherResponse
import com.asap.domain.entity.remote.user.UserProfile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @GET("/")
    suspend fun fetchResultCard(): Response<ResultCard>

    @POST("/profile/check-nick-name")
    suspend fun checkNickname(@Body body: Map<String, String>): Response<CheckNicknameResponse>

    @POST("/profile/save-profile")
    suspend fun saveProfile(@Body body: SaveProfileRequest): Response<SaveProfileResponse>

    @GET("/admin/{user_id}")
    suspend fun fetchUserProfile(@Query("user_id") userId: Int): Response<UserProfile>

    @HTTP(method = "DELETE", path = "/admin/user", hasBody = true)
    suspend fun deleteUser(@Body body: DeleteUserRequestBody): Response<WhetherResponse>

}

