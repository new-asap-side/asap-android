package com.asap.domain.entity.remote.alarm

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlarmOffRate(
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "offRate")
    val offRate: Float
)
