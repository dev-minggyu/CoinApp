package com.example.domain.usecase.ticker

import com.example.domain.repository.ticker.TickerRepository
import javax.inject.Inject

class UnsubscribeTickerUseCase @Inject constructor(private val repository: TickerRepository) {
    suspend fun execute() = repository.unsubscribeTicker()
}