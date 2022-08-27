package com.example.data.repository.ticker.local

import com.example.data.db.ticker.FavoriteTickerDao
import com.example.data.db.ticker.TickerDao
import com.example.data.model.ticker.TickerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TickerLocalDataSourceImpl @Inject constructor(
    private val tickerDao: TickerDao,
    private val favoriteTickerDao: FavoriteTickerDao
) : TickerLocalDataSource {
    override suspend fun getTickers(): List<TickerEntity> =
        withContext(Dispatchers.IO) {
            tickerDao.getTickers()
        }

    override suspend fun insertTickers(tickerList: List<TickerEntity>) {
        withContext(Dispatchers.IO) {
            tickerDao.insertTickers(tickerList)
        }
    }

    override suspend fun deleteAllTickers() {
        withContext(Dispatchers.IO) {
            tickerDao.deleteAllTicker()
        }
    }
}