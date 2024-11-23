package com.asap.domain.entity.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed interface ReleaseAlarmContent {
    data object Drag : ReleaseAlarmContent
    data object Card : ReleaseAlarmContent
}

@JsonClass(generateAdapter = true)
data class Alarm(
    @Json(name = "time")
    val time: String,
    @Json(name = "releaseType")
    val releaseTypeString: String
) {
    val releaseType get() = when (releaseTypeString) {
        "drag" -> ReleaseAlarmContent.Drag
        "card" -> ReleaseAlarmContent.Card
        else -> throw Exception("Unknown release type.")
    }
}