package com.mingg.database.favoriteticker

import com.mingg.database.data.favoriteticker.FavoriteTickerEntity

interface FavoriteTickerLocalDataSource {
    suspend fun getFavoriteTickerList(): List<FavoriteTickerEntity>

    suspend fun insertFavoriteTicker(favoriteTickerEntity: FavoriteTickerEntity)

    suspend fun deleteFavoriteTickerList(symbol: String)
}