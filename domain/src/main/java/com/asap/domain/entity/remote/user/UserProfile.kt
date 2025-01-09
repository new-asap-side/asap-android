package com.asap.domain.entity.remote.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProfile(
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "profile_image_url")
    val profileImageUrl: String,
    @Json(name = "profile_image_url")
    val nickName: String
)
