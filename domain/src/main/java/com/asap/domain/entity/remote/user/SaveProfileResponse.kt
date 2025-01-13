package com.asap.domain.entity.remote.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SaveProfileResponse(
    @Json(name = "result")
    val result: Boolean,
    @Json(name = "reason")
    val reason: String?,
    @Json(name = "profile_image_url")
    val profileImageUrl: String?
)
