package com.ming.domain.repository.favoriteticker

import com.ming.domain.model.favoriteticker.FavoriteTicker

interface FavoriteTickerRepository {
    suspend fun getFavoriteTickerList(): List<FavoriteTicker>

    suspend fun insertFavoriteTicker(symbol: String)

    suspend fun deleteFavoriteTicker(symbol: String)
}