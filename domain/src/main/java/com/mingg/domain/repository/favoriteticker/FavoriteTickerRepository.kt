package com.mingg.domain.repository.favoriteticker

import com.mingg.domain.model.favoriteticker.FavoriteTicker

interface FavoriteTickerRepository {
    suspend fun getFavoriteTickerList(): List<FavoriteTicker>

    suspend fun insertFavoriteTicker(symbol: String)

    suspend fun deleteFavoriteTicker(symbol: String)
}