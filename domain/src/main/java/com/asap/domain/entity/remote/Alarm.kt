package com.asap.domain.entity.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed interface ReleaseAlarmContent {
    data object Drag : ReleaseAlarmContent
    data object Card : ReleaseAlarmContent
}

@JsonClass(generateAdapter = true)
data class Alarm(
    @Json(name = "group_id")
    val groupId: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "time")
    val time: String,
    @Json(name = "alarm_unlock_contents")
    val alarmUnlockContents: String
) {
    val unlockContents get() = when (alarmUnlockContents) {
        "drag" -> ReleaseAlarmContent.Drag
        "card" -> ReleaseAlarmContent.Card
        else -> throw Exception("Unknown release type.")
    }
}