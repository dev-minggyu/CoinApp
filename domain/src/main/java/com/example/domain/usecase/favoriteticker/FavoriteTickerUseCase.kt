package com.example.domain.usecase.favoriteticker

import com.example.domain.repository.favoriteticker.FavoriteTickerRepository
import javax.inject.Inject

class FavoriteTickerUseCase @Inject constructor(private val repository: FavoriteTickerRepository) {
    suspend fun executeInsert(symbol: String) =
        repository.insertFavoriteTicker(symbol)

    suspend fun executeDelete(symbol: String) =
        repository.deleteFavoriteTicker(symbol)
}