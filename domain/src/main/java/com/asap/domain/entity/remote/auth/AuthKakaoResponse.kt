package com.asap.domain.entity.remote.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthKakaoResponse(
    @Json(name = "accessToken")
    override val accessToken: String,
    @Json(name = "refreshToken")
    override val refreshToken: String,
    @Json(name = "kakao_id")
    override val socialLoginId: String,
    @Json(name = "user_id")
    override val userId: String
): AuthResponse()
