package com.example.data.repository.favoriteticker

import com.example.data.mapper.FavoriteTickerMapper
import com.example.data.repository.favoriteticker.local.FavoriteTickerLocalDataSource
import com.example.domain.repository.favoriteticker.FavoriteTickerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteTickerRepositoryImpl @Inject constructor(
    private val favoriteTickerLocalDataSource: FavoriteTickerLocalDataSource,
) : FavoriteTickerRepository {
    override suspend fun insertFavoriteTicker(symbol: String) =
        withContext(Dispatchers.IO) {
            favoriteTickerLocalDataSource.insertFavoriteTicker(
                FavoriteTickerMapper.mapperToFavoriteTickerEntity(symbol)
            )
        }

    override suspend fun deleteFavoriteTicker(symbol: String) =
        withContext(Dispatchers.IO) {
            favoriteTickerLocalDataSource.deleteFavoriteTickerList(symbol)
        }
}