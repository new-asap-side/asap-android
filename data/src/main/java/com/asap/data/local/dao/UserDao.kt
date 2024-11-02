package com.asap.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asap.domain.entity.local.User

@Dao
interface UserDao {
    @Query("SELECT EXISTS(SELECT * FROM User)")
    suspend fun isCached(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg user: User)

    @Query("DELETE FROM User")
    suspend fun deleteAll()
}