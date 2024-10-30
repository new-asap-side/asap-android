package com.asap.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.asap.domain.entity.local.KakaoUser

@Dao
interface UserDao {
    @Query("SELECT EXISTS(SELECT * FROM KakaoUser WHERE KakaoUser.access_token == :accessToken)")
    suspend fun isCached(accessToken: String): Boolean

    @Insert
    suspend fun insert(vararg user: KakaoUser)
}