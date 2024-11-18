package com.asap.data.remote.request

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SaveProfileRequest(
    val userId: Int,
    @Json(name = "nickName")
    val nickname: String,
    @Json(name = "profileImgBase64")
    val profileImg: String
)
