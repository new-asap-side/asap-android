package com.asap.domain.entity.remote.auth

import com.asap.domain.entity.local.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthKakaoResponse(
    @Json(name = "accessToken")
    override val accessToken: String,

    @Json(name = "refreshToken")
    override val refreshToken: String,

    @Json(name = "user_id")
    override val userId: String,

    @Json(name = "kakao_id")
    override val socialLoginId: String,

    @Json(name = "isJoinedUser")
    override val isJoinedUser: Boolean
) : AuthResponse

fun AuthResponse.toKakaoUser(): User = User(
    userId = this.userId,
    kakaoId = this.socialLoginId,
    accessToken = this.accessToken,
    refreshToken = this.refreshToken,
)