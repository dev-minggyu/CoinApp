package com.mingg.domain.usecase.ticker

import com.mingg.domain.model.ticker.Currency
import com.mingg.domain.repository.ticker.TickerRepository
import javax.inject.Inject

class TickerSingleDataUseCase @Inject constructor(private val repository: TickerRepository) {
    suspend fun execute(symbol: String, currency: Currency) =
        repository.observeSingleTicker(symbol, currency)
}