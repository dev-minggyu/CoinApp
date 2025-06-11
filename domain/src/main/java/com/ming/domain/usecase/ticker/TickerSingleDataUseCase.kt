package com.ming.domain.usecase.ticker

import com.ming.domain.model.ticker.Currency
import com.ming.domain.repository.ticker.TickerRepository
import javax.inject.Inject

class TickerSingleDataUseCase @Inject constructor(private val repository: TickerRepository) {
    suspend fun execute(symbol: String, currency: Currency) =
        repository.observeSingleTicker(symbol, currency)
}