package com.example.database.favoriteticker

import com.example.database.data.favoriteticker.FavoriteTickerEntity

interface FavoriteTickerLocalDataSource {
    suspend fun getFavoriteTickerList(): List<FavoriteTickerEntity>

    suspend fun insertFavoriteTicker(favoriteTickerEntity: FavoriteTickerEntity)

    suspend fun deleteFavoriteTickerList(symbol: String)
}