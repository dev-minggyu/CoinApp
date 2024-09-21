package com.mingg.database.favoriteticker

import com.mingg.database.data.favoriteticker.FavoriteTickerDao
import com.mingg.database.data.favoriteticker.FavoriteTickerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteTickerLocalDataSourceImpl @Inject constructor(
    private val favoriteTickerDao: FavoriteTickerDao
) : FavoriteTickerLocalDataSource {
    override suspend fun getFavoriteTickerList(): List<FavoriteTickerEntity> =
        withContext(Dispatchers.IO) {
            favoriteTickerDao.getFavoriteTickerList()
        }

    override suspend fun insertFavoriteTicker(favoriteTickerEntity: FavoriteTickerEntity) {
        withContext(Dispatchers.IO) {
            favoriteTickerDao.insertFavoriteTicker(favoriteTickerEntity)
        }
    }

    override suspend fun deleteFavoriteTickerList(symbol: String) {
        withContext(Dispatchers.IO) {
            favoriteTickerDao.deleteFavoriteSymbol(symbol)
        }
    }
}