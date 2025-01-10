package com.asap.data.remote.service

import com.asap.domain.entity.remote.AuthKakaoBody
import com.asap.domain.entity.remote.auth.AuthKakaoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/kakao")
    suspend fun authKakao(@Body body: AuthKakaoBody): Response<AuthKakaoResponse?>
}