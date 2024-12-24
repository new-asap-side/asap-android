package com.asap.domain.entity.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


sealed class UserGroupType {
    data object Leader: UserGroupType()
    data object Participant: UserGroupType()
    data object NonParticipant: UserGroupType()
}

@JsonClass(generateAdapter = true)
data class GroupDetails(
    @Json(name = "group_id")
    val groupId: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "max_person")
    val maxPerson: Int,
    @Json(name = "current_person")
    val currentPerson: Int,
    @Json(name = "is_public")
    val isPublic: Boolean,
    @Json(name = "alarm_end_date")
    val alarmEndDate: String,
    @Json(name = "alarm_time")
    val alarmTime: String,
    @Json(name = "view_count")
    val viewCount: Int,
    @Json(name = "group_thumbnail_image_url")
    val groupThumbnailImageUrl: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "alarm_unlock_contents")
    val alarmUnlockContents: String,
    @Json(name = "userGroups")
    val users: List<GroupMember>,
    @Json(name = "alarm_days")
    val alarmDays: List<String>,
    val userType: String
) {
    val userGroupType: UserGroupType get() = when(userType) {
        "leader" -> UserGroupType.Leader
        "participant" -> UserGroupType.Participant
        else -> UserGroupType.NonParticipant
    }
}
