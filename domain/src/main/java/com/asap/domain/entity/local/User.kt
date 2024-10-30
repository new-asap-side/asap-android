package com.asap.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class KakaoUser(
    @PrimaryKey
    @ColumnInfo(name = "access_token")
    val accessToken: String,
    @ColumnInfo(name = "refresh_token")
    val refreshToken: String,
    @ColumnInfo(name = "id_token")
    val idToken: String?
)
