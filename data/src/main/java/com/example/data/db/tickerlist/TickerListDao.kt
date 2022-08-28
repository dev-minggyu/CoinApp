package com.example.data.db.tickerlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.tickerlist.TickerListEntity

@Dao
interface TickerListDao {
    @Query("SELECT * FROM ticker_name")
    suspend fun getTickerList(): List<TickerListEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTickerList(tickerList: List<TickerListEntity>): List<Long>

    @Query("DELETE FROM ticker_name")
    suspend fun deleteTickerList(): Int
}