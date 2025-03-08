package com.asap.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Recent_search_history")
data class SearchEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("query")
    val query: String,
    @ColumnInfo("date")
    val date: String,
)
