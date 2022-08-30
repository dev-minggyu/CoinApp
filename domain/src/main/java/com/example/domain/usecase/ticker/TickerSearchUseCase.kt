package com.example.domain.usecase.ticker

import com.example.domain.model.ticker.Ticker
import com.example.domain.repository.ticker.TickerRepository
import javax.inject.Inject

class TickerSearchUseCase @Inject constructor(private val repository: TickerRepository) {
    suspend fun execute(searchSymbol: String): List<Ticker> = repository.searchTickerList(searchSymbol)
}