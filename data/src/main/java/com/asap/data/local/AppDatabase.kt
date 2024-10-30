package com.asap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.asap.data.local.dao.UserDao
import com.asap.domain.entity.local.KakaoUser

@Database(entities = [KakaoUser::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}