package com.example.data.repository.ticker.local

import com.example.data.model.ticker.TickerEntity

interface TickerLocalDataSource {
    suspend fun getTickers(): List<TickerEntity>

    suspend fun insertTickers(tickerList: List<TickerEntity>)

    suspend fun deleteAllTickers()
}