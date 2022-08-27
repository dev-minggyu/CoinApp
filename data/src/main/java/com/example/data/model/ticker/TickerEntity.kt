package com.example.data.model.ticker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ticker"
)
data class TickerEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "market")
    val market: String,

    @ColumnInfo(name = "englishName")
    val englishName: String,

    @ColumnInfo(name = "koreanName")
    val koreanName: String
)