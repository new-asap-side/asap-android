package com.asap.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asap.domain.entity.local.SearchEntity

@Dao
interface SearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(search: SearchEntity)

    @Query("SELECT * FROM SearchList")
    suspend fun selectAll(): List<SearchEntity>

    @Query("DELETE FROM SearchList WHERE `query` = :query")
    suspend fun deleteByQury(query: String)

    @Query("DELETE FROM SearchList")
    suspend fun deleteAll()
}