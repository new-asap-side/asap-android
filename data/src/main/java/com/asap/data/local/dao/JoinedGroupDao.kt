package com.asap.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asap.domain.entity.local.JoinedAlarmEntity

@Dao
interface JoinedGroupDao {
    @Query("SELECT * FROM Joined_alarms")
    suspend fun selectAll(): List<JoinedAlarmEntity>

    @Query("SELECT * FROM Joined_alarms WHERE group_id = :groupId")
    suspend fun find(groupId: Int): JoinedAlarmEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg a: JoinedAlarmEntity)

    @Delete
    suspend fun delete(deactivatedAlarm: JoinedAlarmEntity)
}