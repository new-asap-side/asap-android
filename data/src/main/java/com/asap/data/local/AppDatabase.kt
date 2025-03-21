package com.asap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.asap.data.local.dao.DeactivatedAlarmDao
import com.asap.data.local.dao.SearchDao
import com.asap.data.local.dao.UserDao
import com.asap.domain.entity.local.AlarmEntity
import com.asap.domain.entity.local.SearchEntity
import com.asap.domain.entity.local.User

@Database(
    version = 4,
    entities = [User::class, AlarmEntity::class, SearchEntity::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun deactivatedAlarmDao(): DeactivatedAlarmDao

    abstract fun searchDao(): SearchDao
}