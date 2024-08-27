package com.mingg.database.data.favoriteticker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ticker_favorite",
    indices = [Index(value = ["symbol"], unique = true)]
)
data class FavoriteTickerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "symbol")
    var symbol: String = ""
)