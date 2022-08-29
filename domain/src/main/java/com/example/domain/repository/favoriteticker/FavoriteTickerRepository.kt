package com.example.domain.repository.favoriteticker

interface FavoriteTickerRepository {
    suspend fun insertFavoriteTicker(symbol: String)

    suspend fun deleteFavoriteTicker(symbol: String)
}