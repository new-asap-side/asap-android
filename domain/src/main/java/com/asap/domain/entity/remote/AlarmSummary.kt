package com.asap.domain.entity.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlarmSummary(
    @Json(name = "group_id")
    val groupId: Int,
    @Json(name = "group")
    val group: AlarmInfomation
)

@JsonClass(generateAdapter = true)
data class AlarmInfomation(
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "alarm_end_date")
    val alarmEndDate: String,
    @Json(name = "alarm_time")
    val alarmTime: String,
    @Json(name = "alarm_days")
    val alarmDays: List<String>,
) {
    fun parse(day: String): String = "$day $alarmTime"
}