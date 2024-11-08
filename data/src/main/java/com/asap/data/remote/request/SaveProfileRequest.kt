package com.asap.data.remote.request

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SaveProfileRequest(
    val userId: Int,
    val nickname: String,
    @Json(name = "profile_img")
    val profileImg: String
)
