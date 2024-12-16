package com.asap.data.remote.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostGroupCreateRequest(
    @Json(name = "base64_group_img")
    val groupImage: String,
    @Json(name = "alarm_days")
    val alarmDays: List<String>,
    @Json(name = "alarm_end_date")
    val alarmEndDate: String,
    @Json(name = "alarm_time")
    val alarmTime: String,
    @Json(name = "alarm_type")
    val alarmType: String,
    @Json(name = "alarm_unlock_contents")
    val alarmUnlockContents: String,
    @Json(name = "alarm_volume")
    val alarmVolume: Int,
    @Json(name = "description")
    val description: String,
    @Json(name = "device_token")
    val deviceToken: String,
    @Json(name = "device_type")
    val deviceType: String,
    @Json(name = "group_password")
    val groupPassword: String?,
    @Json(name = "is_public")
    val isPublic: Boolean,
    @Json(name = "max_person")
    val maxPerson: Int,
    @Json(name = "music_title")
    val musicTitle: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "user_id")
    val userId: Int
)