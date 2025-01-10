package com.asap.data.remote.service

import com.asap.data.remote.HeaderInterceptor
import com.asap.domain.entity.remote.AuthKakaoBody
import com.asap.domain.entity.remote.auth.AuthKakaoResponse
import com.asap.domain.entity.remote.auth.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/kakao")
    suspend fun authKakao(@Body body: AuthKakaoBody): Response<AuthKakaoResponse?>

    @POST("/auth/refresh")
    suspend fun refreshToken(
        @Header(HeaderInterceptor.AUTH_KEY) refreshToken: String
    ): Response<RefreshTokenResponse?>
}