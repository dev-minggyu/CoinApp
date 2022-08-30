package com.example.data.repository.favoriteticker

import com.example.data.mapper.favoriteticker.FavoriteTickerMapper
import com.example.data.repository.favoriteticker.local.FavoriteTickerLocalDataSource
import com.example.domain.model.favoriteticker.FavoriteTicker
import com.example.domain.model.ticker.AtomicTickerList
import com.example.domain.repository.favoriteticker.FavoriteTickerRepository
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
                FavoriteTickerMapper.mapperToFavoriteTicker(it)
            }
        }

    override suspend fun insertFavoriteTicker(symbol: String) =
        withContext(Dispatchers.IO) {
            atomicTickerList.updateFavorite(symbol, true)
            favoriteTickerLocalDataSource.insertFavoriteTicker(
                FavoriteTickerMapper.mapperToFavoriteTickerEntity(symbol)
            )
        }

    override suspend fun deleteFavoriteTicker(symbol: String) =
        withContext(Dispatchers.IO) {
            atomicTickerList.updateFavorite(symbol, false)
            favoriteTickerLocalDataSource.deleteFavoriteTickerList(symbol)
        }
}