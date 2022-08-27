package com.example.data.db.ticker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.ticker.TickerEntity

@Dao
interface TickerDao {
    @Query("SELECT * FROM ticker")
    suspend fun getTickers(): List<TickerEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTickers(tickerList: List<TickerEntity>): List<Long>

    @Query("DELETE FROM ticker")
    suspend fun deleteAllTicker(): Int
}