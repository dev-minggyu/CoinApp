package com.example.data.model.tickerlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "ticker_name"
)
data class TickerListEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "market")
    val market: String,

    @ColumnInfo(name = "englishName")
    val englishName: String,

    @ColumnInfo(name = "koreanName")
    val koreanName: String
)