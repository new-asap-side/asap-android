package com.asap.data.remote.service

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
}

