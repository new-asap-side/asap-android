package com.asap.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.asap.domain.entity.local.DeactivatedAlarm

@Dao
interface DeactivatedAlarmDao {
    @Query("SELECT * FROM DeactivatedAlarm")
    suspend fun getDeactivatedAlarmList(): List<DeactivatedAlarm>

    @Query("SELECT * FROM DeactivatedAlarm WHERE group_id = :groupId")
    suspend fun find(groupId: Int): DeactivatedAlarm?

    @Insert
    suspend fun insert(deactivatedAlarm: DeactivatedAlarm)

    @Delete
    suspend fun delete(deactivatedAlarm: DeactivatedAlarm)
}