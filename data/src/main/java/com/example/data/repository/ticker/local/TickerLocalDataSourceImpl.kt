package com.example.data.repository.ticker.local

import com.example.data.db.ticker.FavoriteTickerDao
import javax.inject.Inject

class TickerLocalDataSourceImpl @Inject constructor(private val favoriteTickerDao: FavoriteTickerDao) : TickerLocalDataSource {

}