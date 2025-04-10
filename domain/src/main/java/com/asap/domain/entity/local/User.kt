package com.asap.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    @ColumnInfo(name = "kakao_id")
    val kakaoId: String,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "access_token")
    val accessToken: String,
    @ColumnInfo(name = "refresh_token")
    val refreshToken: String,
    @ColumnInfo(name = "profile_image")
    val profileImg: String? = null,
    @ColumnInfo(name = "profile_item")
    val profileItem: String? = null,
    @ColumnInfo(name = "nickname")
    val nickname: String? = null,
)