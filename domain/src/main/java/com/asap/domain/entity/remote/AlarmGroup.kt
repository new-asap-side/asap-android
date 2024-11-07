package com.asap.domain.entity.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlarmGroup(
    @Json(name = "isPublic")
    val isPublic: Boolean,
    @Json(name = "thumbnailUrl")
    val thumbnailUrl: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "alarmDate")
    val alarmDates: List<String>,
    @Json(name = "alarmTime")
    val alarmTime: String,
    @Json(name = "totalNumber")
    val totalNumber: Int,
    @Json(name = "currentNumber")
    val currentNumber: Int,
) {
    companion object {
        fun dummy(): AlarmGroup {
            return AlarmGroup(
                isPublic = true,
                thumbnailUrl = "",
                title = "TEST Group",
                alarmDates = listOf("월", "화", "수"),
                alarmTime = "21:00",
                totalNumber = 8,
                currentNumber = 4,
            )
        }
    }
}