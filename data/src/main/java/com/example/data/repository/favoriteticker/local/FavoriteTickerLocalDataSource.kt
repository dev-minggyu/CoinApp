package com.example.data.repository.favoriteticker.local

import com.example.data.model.favoriteticker.FavoriteTickerEntity

interface FavoriteTickerLocalDataSource {
    suspend fun getFavoriteTickerList(): List<FavoriteTickerEntity>

    suspend fun insertFavoriteTicker(favoriteTickerEntity: FavoriteTickerEntity)

    suspend fun deleteFavoriteTickerList(symbol: String)
}