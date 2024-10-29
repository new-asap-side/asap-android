package com.asap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.asap.data.local.dao.UserDao
import com.asap.domain.entity.local.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}