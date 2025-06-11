package com.ming.database.favoriteticker

import com.ming.database.data.favoriteticker.FavoriteTickerEntity

interface FavoriteTickerLocalDataSource {
    suspend fun getFavoriteTickerList(): List<FavoriteTickerEntity>

    suspend fun insertFavoriteTicker(favoriteTickerEntity: FavoriteTickerEntity)

    suspend fun deleteFavoriteTickerList(symbol: String)
}