package com.example.data.db.tickersymbol

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.tickersymbol.TickerSymbolEntity

@Dao
interface TickerSymbolDao {
    @Query("SELECT * FROM ticker_symbol")
    suspend fun getTickerSymbolList(): List<TickerSymbolEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTickerSymbolList(tickerSymbolList: List<TickerSymbolEntity>): List<Long>

    @Query("DELETE FROM ticker_symbol")
    suspend fun deleteTickerSymbolList(): Int
}