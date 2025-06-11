package com.ming.repository

import com.ming.database.favoriteticker.FavoriteTickerLocalDataSource
import com.ming.domain.model.favoriteticker.FavoriteTicker
import com.ming.domain.model.ticker.AtomicTickerList
import com.ming.domain.repository.favoriteticker.FavoriteTickerRepository
import com.ming.network.mapper.favoriteticker.FavoriteTickerMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteTickerRepositoryImpl @Inject constructor(
    private val favoriteTickerLocalDataSource: FavoriteTickerLocalDataSource,
    private val atomicTickerList: AtomicTickerList
) : FavoriteTickerRepository {
    override suspend fun getFavoriteTickerList(): List<FavoriteTicker> =
        withContext(Dispatchers.IO) {
            favoriteTickerLocalDataSource.getFavoriteTickerList().map {
                FavoriteTickerMapper.toFavoriteTicker(it)
            }
        }

    override suspend fun insertFavoriteTicker(symbol: String) =
        withContext(Dispatchers.IO) {
            atomicTickerList.updateFavorite(symbol, true)
            favoriteTickerLocalDataSource.insertFavoriteTicker(
                FavoriteTickerMapper.toFavoriteTickerEntity(symbol)
            )
        }

    override suspend fun deleteFavoriteTicker(symbol: String) =
        withContext(Dispatchers.IO) {
            atomicTickerList.updateFavorite(symbol, false)
            favoriteTickerLocalDataSource.deleteFavoriteTickerList(symbol)
        }
}