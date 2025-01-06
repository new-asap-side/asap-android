package com.asap.data.remote.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostPersonalEditRequest (
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "group_id")
    val groupId: Int,
    @Json(name = "alarm_type")
    val alarmType: String,
    @Json(name = "alarm_volume")
    val alarmVolume: Int?,
    @Json(name = "music_title")
    val musicTitle: String?,
)