package com.asap.domain.entity.main.home

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ResultCard(
    @Json(name = "successRate")
    val successRate: Float = 0.0f,
    @Json(name = "participatingGroup")
    val participatingGroup: Int = 0
)
