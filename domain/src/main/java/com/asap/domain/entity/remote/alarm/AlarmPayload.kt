package com.asap.domain.entity.remote.alarm

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

sealed interface AlarmUnlockContent {
    data object Slide : AlarmUnlockContent
    data object Card : AlarmUnlockContent
    data object Calculator : AlarmUnlockContent
}

@JsonClass(generateAdapter = true)
@Parcelize
data class AlarmPayload(
    @Json(name = "group_id")
    val groupId: Int,
    @Json(name = "group_title")
    val groupTitle: String,
    @Json(name = "music_title")
    val musicTitle: String?,
    @Json(name = "music_volume")
    val musicVolume: Float?,
    @Json(name = "alarm_type")
    val alarmType: String,
    @Json(name = "alarm_unlock_contents")
    val alarmUnlockContent: String
): Parcelable {
    val content get() = when (alarmUnlockContent) {
        "SLIDE" -> AlarmUnlockContent.Slide
        "CARD" -> AlarmUnlockContent.Card
        "CALCULATION" -> AlarmUnlockContent.Calculator
        else -> throw Exception("Unknown unlock alarm type.")
    }
}
