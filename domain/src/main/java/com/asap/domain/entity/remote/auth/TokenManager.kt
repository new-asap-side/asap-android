package com.asap.domain.entity.remote.auth

// 메모리 캐시용 object
object TokenManager {
    var fcmToken: String = ""
    var accessToken: String? = null
    var refreshToken: String? = null
}