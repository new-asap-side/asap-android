package com.asap.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE USER(" +
                "kakao_id TEXT PRIMARY KEY NOT NULL," +
                "user_id TEXT NOT NULL," +
                "access_token TEXT NOT NULL," +
                "refresh_token TEXT NOT NULL" +
                ")")
        db.execSQL("DROP TABLE KakaoUser")
    }
}