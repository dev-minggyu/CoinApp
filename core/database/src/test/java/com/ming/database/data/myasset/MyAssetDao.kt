package com.ming.database.data.myasset

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ming.database.data.myasset.MyTickerEntity

@Dao
interface MyAssetDao {
    @Query("SELECT * FROM my_asset")
    suspend fun getMyAssetList(): List<MyTickerEntity>

    @Query("SELECT * FROM my_asset WHERE symbol = :symbol and currency = :currency")
    suspend fun getMyAssetTicker(symbol: String, currency: String): MyTickerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyTicker(ticker: MyTickerEntity): Long

    @Query("DELETE FROM my_asset WHERE symbol = :symbol and currency = :currency")
    suspend fun deleteMyTicker(symbol: String, currency: String): Int

    @Query("DELETE FROM my_asset")
    suspend fun deleteAllMyTicker(): Int
}