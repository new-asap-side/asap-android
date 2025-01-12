package com.asap.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.asap.domain.entity.local.AlarmEntity

@Dao
interface DeactivatedAlarmDao {
    @Query("SELECT * FROM Deactivated_alarm")
    suspend fun getDeactivatedAlarmList(): List<AlarmEntity>

    @Query("SELECT * FROM Deactivated_alarm WHERE group_id = :groupId")
    suspend fun find(groupId: Int): AlarmEntity?

    @Insert
    suspend fun insert(deactivatedAlarm: AlarmEntity)

    @Delete
    suspend fun delete(deactivatedAlarm: AlarmEntity)
}