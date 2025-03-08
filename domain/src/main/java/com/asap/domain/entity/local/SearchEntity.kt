package com.asap.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SearchList")
data class SearchEntity(
    @PrimaryKey
    @ColumnInfo("query")
    val query: String,
    @ColumnInfo("date")
    val date: String,
)
