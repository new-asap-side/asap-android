package com.asap.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SaveProfileResponse(
    val result: Boolean,
    val reason: String?,
    @Json(name = "profile_image_url")
    val profileImageUrl: String
)
