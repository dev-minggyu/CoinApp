package com.example.data.model.tickersymbol

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "ticker_symbol"
)
data class TickerSymbolEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "market")
    val market: String,

    @ColumnInfo(name = "englishName")
    val englishName: String,

    @ColumnInfo(name = "koreanName")
    val koreanName: String
)