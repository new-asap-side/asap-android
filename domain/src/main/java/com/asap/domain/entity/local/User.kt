package com.asap.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val accessToken: String,
    @ColumnInfo(name = "user_id")
    val userId: String
)
