package com.asap.domain.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KakaoLoginResponse(
    @Json(name = "accessToken")
    val accessToken: String,
    @Json(name = "refreshToken")
    val refreshToken: String,
    @Json(name = "kakao_id")
    val kakaoId: String,
    @Json(name = "user_id")
    val userId: String
)
