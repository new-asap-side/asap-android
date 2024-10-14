package com.asap.data.network.service

import retrofit2.Response
import retrofit2.http.GET

interface LoginService {
    @GET("/auth/kakao-login-page")
    suspend fun login(): Response<Unit>
}

