package com.example.data.repository.tickersymbol.local

import com.example.data.model.tickersymbol.TickerSymbolEntity

interface TickerSymbolLocalDataSource {
    suspend fun getTickerSymbolList(): List<TickerSymbolEntity>

    suspend fun insertTickerSymbolList(tickerSymbolList: List<TickerSymbolEntity>)

    suspend fun deleteTickerSymbolList()
}