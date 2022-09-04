package com.example.data.db.myasset

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.myasset.MyTickerEntity

@Dao
interface MyAssetDao {
    @Query("SELECT * FROM my_asset")
    suspend fun getMyAssetList(): List<MyTickerEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMyTicker(ticker: MyTickerEntity): Long

    @Query("DELETE FROM my_asset WHERE symbol = :symbol")
    suspend fun deleteMyTicker(symbol: String): Int

    @Query("DELETE FROM my_asset")
    suspend fun deleteAllMyTicker(): Int
}