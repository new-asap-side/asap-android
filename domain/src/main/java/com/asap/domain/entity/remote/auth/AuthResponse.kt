package com.asap.domain.entity.remote.auth


interface AuthResponse {
    val accessToken: String
    val refreshToken: String
    val userId: String
    val socialLoginId: String
}
