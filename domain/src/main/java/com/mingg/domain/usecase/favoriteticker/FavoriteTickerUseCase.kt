package com.mingg.domain.usecase.favoriteticker

import com.mingg.domain.repository.favoriteticker.FavoriteTickerRepository
import javax.inject.Inject

class FavoriteTickerUseCase @Inject constructor(private val repository: FavoriteTickerRepository) {
    suspend fun executeInsert(symbol: String) =
        repository.insertFavoriteTicker(symbol)

    suspend fun executeDelete(symbol: String) =
        repository.deleteFavoriteTicker(symbol)
}