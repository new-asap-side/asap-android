package com.asap.domain.entity.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GroupJoinRequest(
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "group_id")
    val groupId: Int,
    @Json(name = "device_token")
    val deviceToken: String,
    @Json(name = "group_password")
    val groupPassword: String,
    @Json(name = "alarm_type")
    val alarmType: String = "SOUND",
    @Json(name = "device_type")
    val deviceType: String = "ANDROID"
)

@JsonClass(generateAdapter = true)
data class GroupJoinResponse(
    @Json(name = "groupId")
    val groupId: Int,
    @Json(name = "message")
    val message: String
)