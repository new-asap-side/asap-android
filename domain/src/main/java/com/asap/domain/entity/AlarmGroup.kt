package com.asap.domain.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlarmGroup(
    @Json(name = "groupState")
    val groupState: Boolean,
    @Json(name = "thumbnailUrl")
    val thumbnailUrl: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "alarmDate")
    val alarmDate: String,
    @Json(name = "alarmTime")
    val alarmTime: String,
    @Json(name = "number")
    val number: Int
)
