package com.asap.data.remote.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostGroupEditRequest(
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "group_id")
    val groupId: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "max_person")
    val maxPerson: Int,
    @Json(name = "alarm_unlock_contents")
    val alarmUnlockContents: String,
    @Json(name = "is_public")
    val isPublic: Boolean,
    @Json(name = "group_password")
    val groupPassword: String?,
    @Json(name = "base64_group_img")
    val groupImage: String
)
