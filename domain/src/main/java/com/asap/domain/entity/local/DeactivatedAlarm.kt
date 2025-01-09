package com.asap.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * 비활성화 알람 정보
 */
@Entity
data class DeactivatedAlarm(
    @PrimaryKey
    @ColumnInfo("group_id")
    val groupId: Int,
    @ColumnInfo("group_title")
    val groupTitle: String,
    @ColumnInfo("alarm_time")
    val alarmTime: String,
    @ColumnInfo("alarm_dates")
    val alarmDates: String
)
