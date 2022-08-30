package com.example.domain.repository.favoriteticker

import com.example.domain.model.favoriteticker.FavoriteTicker

interface FavoriteTickerRepository {
    suspend fun getFavoriteTickerList(): List<FavoriteTicker>

    suspend fun insertFavoriteTicker(symbol: String)

    suspend fun deleteFavoriteTicker(symbol: String)
}