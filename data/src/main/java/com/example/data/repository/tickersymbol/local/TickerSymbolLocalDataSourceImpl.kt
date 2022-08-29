package com.example.data.repository.tickersymbol.local

import com.example.data.db.tickersymbol.TickerSymbolDao
import com.example.data.model.tickersymbol.TickerSymbolEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TickerSymbolLocalDataSourceImpl @Inject constructor(
    private val tickerSymbolDao: TickerSymbolDao
) : TickerSymbolLocalDataSource {
    override suspend fun getTickerSymbolList(): List<TickerSymbolEntity> =
        withContext(Dispatchers.IO) {
            tickerSymbolDao.getTickerSymbolList()
        }

    override suspend fun insertTickerSymbolList(tickerSymbolList: List<TickerSymbolEntity>) {
        withContext(Dispatchers.IO) {
            tickerSymbolDao.insertTickerSymbolList(tickerSymbolList)
        }
    }

    override suspend fun deleteTickerSymbolList() {
        withContext(Dispatchers.IO) {
            tickerSymbolDao.deleteTickerSymbolList()
        }
    }
}