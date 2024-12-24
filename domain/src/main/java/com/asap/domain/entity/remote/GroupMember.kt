package com.asap.domain.entity.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GroupMember(
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "alarm_type")
    val alarmType: String,
    @Json(name = "volume")
    val volume: Int,
    @Json(name = "is_group_master")
    val isGroupMaster: Boolean,
    @Json(name = "user")
    val user: MemberSetting
)

@JsonClass(generateAdapter = true)
data class MemberSetting(
    @Json(name = "nick_name")
    val nickName: String,
    @Json(name = "profile_image_url")
    val profileImageUrl: String
)
