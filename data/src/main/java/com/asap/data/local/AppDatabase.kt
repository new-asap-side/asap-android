package com.asap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.asap.data.local.dao.DeactivatedAlarmDao
import com.asap.data.local.dao.UserDao
import com.asap.domain.entity.local.DeactivatedAlarm
import com.asap.domain.entity.local.User

@Database(
    entities = [User::class, DeactivatedAlarm::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun deactivatedAlarmDao(): DeactivatedAlarmDao
}