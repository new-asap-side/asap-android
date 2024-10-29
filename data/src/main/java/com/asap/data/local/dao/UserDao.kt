package com.asap.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.asap.domain.entity.local.User

@Dao
interface UserDao {
    @Query("SELECT EXISTS(SELECT * FROM User WHERE User.kakaoId == user.kakaoId)")
    suspend fun isCached(user: User): Boolean

    @Insert
    suspend fun insert(vararg user: User)

    @Delete
    suspend fun delete(user: User)
}