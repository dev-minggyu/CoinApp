package com.example.data.repository.tickerlist.local

import com.example.data.db.favorite.FavoriteTickerDao
import com.example.data.db.tickerlist.TickerListDao
import com.example.data.model.tickerlist.TickerListEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TickerListLocalDataSourceImpl @Inject constructor(
    private val tickerListDao: TickerListDao,
    private val favoriteTickerDao: FavoriteTickerDao
) : TickerListLocalDataSource {
    override suspend fun getTickerList(): List<TickerListEntity> =
        withContext(Dispatchers.IO) {
            tickerListDao.getTickerList()
        }

    override suspend fun insertTickerList(tickerList: List<TickerListEntity>) {
        withContext(Dispatchers.IO) {
            tickerListDao.insertTickerList(tickerList)
        }
    }

    override suspend fun deleteTickerList() {
        withContext(Dispatchers.IO) {
            tickerListDao.deleteTickerList()
        }
    }
}