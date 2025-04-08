package com.asap.data.remote.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateTokenBody(
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "alarmToken")
    val alarmToken: String,
    @Json(name = "device_type")
    val deviceType: String = "ANDROID"
)
