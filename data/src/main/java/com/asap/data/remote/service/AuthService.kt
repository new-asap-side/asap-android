package com.asap.data.remote.service

import com.asap.data.remote.HeaderInterceptor
import com.asap.data.remote.request.PatchAlarmTokenBody
import com.asap.domain.entity.remote.AuthKakaoBody
import com.asap.domain.entity.remote.WhetherResponse
import com.asap.domain.entity.remote.auth.AuthKakaoResponse
import com.asap.domain.entity.remote.auth.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {
    @PATCH("/admin/alarm_tokne")
    suspend fun patchAlarmToken(@Body body: PatchAlarmTokenBody): Response<WhetherResponse>

    @POST("/auth/kakao")
    suspend fun authKakao(@Body body: AuthKakaoBody): Response<AuthKakaoResponse?>

    @POST("/auth/refresh")
    suspend fun refreshToken(
        @Header(HeaderInterceptor.AUTH_KEY) refreshToken: String
    ): Response<RefreshTokenResponse?>
}