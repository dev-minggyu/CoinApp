package com.example.domain.usecase.ticker

import com.example.domain.repository.ticker.TickerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TickerLoadCompleteUseCase @Inject constructor(private val repository: TickerRepository) {
    suspend fun execute(): Flow<Boolean> = repository.observeLoadComplete()
}