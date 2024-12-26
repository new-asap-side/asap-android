package com.asap.domain.entity.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

sealed interface AlarmUnlockContent {
    data object Drag : AlarmUnlockContent
    data object Card : AlarmUnlockContent
}

@JsonClass(generateAdapter = true)
@Parcelize
data class Alarm(
    @Json(name = "group_id")
    val groupId: Int = 0,
    @Json(name = "title")
    val title: String = "title",
    @Json(name = "alarm_time")
    val alarmTime: String = "21:30",
    @Json(name = "alarm_day")
    val alarmDay: String = "월",
//    @Json(name = "alarm_unlock_content")
    @Json(name = "alarmUnlockContents")
    val alarmUnlockContents: String = "drag"
): Parcelable {
    val content get() = when (alarmUnlockContents) {
        "drag" -> AlarmUnlockContent.Drag
        "card" -> AlarmUnlockContent.Card
        else -> throw Exception("Unknown unlock alarm type.")
    }

    fun parse(day: String): String = "$day $alarmTime"
}