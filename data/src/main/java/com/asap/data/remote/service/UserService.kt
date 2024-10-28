package com.asap.data.remote.service

import com.asap.domain.entity.KakaoLoginResponse
import com.asap.domain.entity.ResultCard
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserService {
    @Headers("Content-Type: application/json")
    @GET("/auth/kakao")
    suspend fun kakaoLogin(): Response<KakaoLoginResponse>

    @GET("/")
    suspend fun fetchResultCard(): Response<ResultCard>
}

