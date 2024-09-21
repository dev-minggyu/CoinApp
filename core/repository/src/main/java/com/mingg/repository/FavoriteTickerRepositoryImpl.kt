package com.mingg.repository

import com.mingg.database.favoriteticker.FavoriteTickerLocalDataSource
import com.mingg.domain.model.favoriteticker.FavoriteTicker
import com.mingg.domain.model.ticker.AtomicTickerList
import com.mingg.domain.repository.favoriteticker.FavoriteTickerRepository
import com.mingg.network.mapper.favoriteticker.FavoriteTickerMapper
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