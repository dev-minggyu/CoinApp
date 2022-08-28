package com.example.data.repository.tickerlist.local

import com.example.data.model.tickerlist.TickerListEntity

interface TickerListLocalDataSource {
    suspend fun getTickerList(): List<TickerListEntity>

    suspend fun insertTickerList(tickerList: List<TickerListEntity>)

    suspend fun deleteTickerList()
}