package com.example.domain.usecase.favoriteticker

import com.example.domain.model.ticker.AtomicTickerList
import com.example.domain.repository.favoriteticker.FavoriteTickerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApplyFavoriteTickerUseCase @Inject constructor(
    private val repository: FavoriteTickerRepository,
    private val atomicTickerList: AtomicTickerList
) {
    suspend fun execute() {
        withContext(Dispatchers.IO) {
            repository.getFavoriteTickerList().forEach {
                atomicTickerList.updateFavorite(it.symbol, true)
            }
        }
    }
}