package com.asap.domain.entity.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed interface AlarmUnlockContent {
    data object Drag : AlarmUnlockContent
    data object Card : AlarmUnlockContent
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
        "drag" -> AlarmUnlockContent.Drag
        "card" -> AlarmUnlockContent.Card
        else -> throw Exception("Unknown release type.")
    }

    fun parse(day: String): String = "$day $alarmTime"
}