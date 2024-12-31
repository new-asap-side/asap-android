package com.asap.domain.entity.remote.auth

abstract class AuthResponse {
    abstract val accessToken: String
    abstract val refreshToken: String
    abstract val socialLoginId: String
    abstract val userId: String
}
