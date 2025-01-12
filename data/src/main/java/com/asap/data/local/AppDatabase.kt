package com.asap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.asap.data.local.dao.DeactivatedAlarmDao
import com.asap.data.local.dao.JoinedGroupDao
import com.asap.data.local.dao.UserDao
import com.asap.domain.entity.local.AlarmEntity
import com.asap.domain.entity.local.JoinedAlarmEntity
import com.asap.domain.entity.local.User

@Database(
    entities = [User::class, AlarmEntity::class, JoinedAlarmEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun deactivatedAlarmDao(): DeactivatedAlarmDao

    abstract fun joinedGroupDao(): JoinedGroupDao
}