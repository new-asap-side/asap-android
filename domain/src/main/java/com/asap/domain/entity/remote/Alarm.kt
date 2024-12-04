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
    val groupId: Int = 0,
    @Json(name = "title")
    val title: String = "title",
    @Json(name = "alarm_time")
    val alarmTime: String = "21:30",
    @Json(name = "alarm_day")
    val alarmDay: String = "월",
    @Json(name = "alarm_unlock_contents")
    val alarmUnlockContents: String = "card"
) {
    val unlockContents get() = when (alarmUnlockContents) {
        "drag" -> ReleaseAlarmContent.Drag
        "card" -> ReleaseAlarmContent.Card
        else -> throw Exception("Unknown release type.")
    }
}