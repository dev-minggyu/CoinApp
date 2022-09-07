package com.example.domain.usecase.ticker

import com.example.domain.model.ticker.FloatingTicker
import com.example.domain.repository.ticker.TickerRepository
import javax.inject.Inject

class GetSymbolListUseCase @Inject constructor(private val repository: TickerRepository) {
    suspend fun execute(): List<FloatingTicker> = repository.getSymbolList()
}