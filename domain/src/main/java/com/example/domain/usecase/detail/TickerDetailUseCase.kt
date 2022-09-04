package com.example.domain.usecase.detail

import com.example.domain.model.ticker.Currency
import com.example.domain.repository.ticker.TickerRepository
import javax.inject.Inject

class TickerDetailUseCase @Inject constructor(private val repository: TickerRepository) {
    suspend fun execute(symbol: String, currency: Currency) =
        repository.observeSingleTicker(symbol, currency)
}