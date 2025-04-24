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

sealed class UserState {
    // 프로필 정보가 있는 유저
    data object ParticipationUser: UserState()

    // 프로필 정보가 없는 유저
    data object NonParticipationUser: UserState()
}