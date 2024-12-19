package com.asap.domain.entity.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class AlarmGroup(
    @Json(name = "group_id")
    val groupId: Int = 0,
    @Json(name = "title")
    val title: String = "Title",
    @Json(name = "description")
    val description: String = "",
    @Json(name = "max_person")
    val maxPersion: Int = 0,
    @Json(name = "current_person")
    val currentPerson: Int = 0,
    @Json(name = "is_public")
    val isPublic: Boolean = true,
    @Json(name = "group_password")
    val groupPasswrod: String? = null,
    @Json(name = "alarm_end_date")
    val alarmEndDate: String = "",
    @Json(name = "alarm_time")
    val alarmTime: String = "21:30",
    @Json(name = "alarm_days")
    val alarmDays: List<String> = listOf("월", "화", "수", "목", "금"),
    @Json(name = "view_count")
    val viewCount: Int = 0,
    @Json(name = "group_thumbnail_image_url")
    val thumbnailUrl: String = "",
    @Json(name = "status")
    val status: String = "",
    @Json(name = "alarm_unlock_contents")
    val alarmUnlockContent: String = "CARD",
    @Json(name = "created_at")
    val createDate: String = "",
    @Json(name = "updated_at")
    val udateDate: String = "",
) : Parcelable {
    companion object {
        fun dummy(): AlarmGroup = AlarmGroup()
    }
}