package com.mingg.database.data.myasset

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "my_asset",
    indices = [Index(value = ["symbol"], unique = true)]
)
data class MyTickerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "symbol")
    var symbol: String,

    @ColumnInfo(name = "currency")
    var currency: String,

    @ColumnInfo(name = "koreanSymbol")
    val koreanSymbol: String,

    @ColumnInfo(name = "englishSymbol")
    val englishSymbol: String,

    @ColumnInfo(name = "amount")
    var amount: String,

    @ColumnInfo(name = "averagePrice")
    var averagePrice: String
)