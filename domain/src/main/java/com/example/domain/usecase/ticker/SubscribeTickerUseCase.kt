package com.example.domain.usecase.ticker

import com.example.domain.repository.ticker.TickerRepository
import com.example.domain.utils.Resource
import javax.inject.Inject

class SubscribeTickerUseCase @Inject constructor(private val repository: TickerRepository) {
    suspend fun execute(receiveDelayMillis: Long = 300L): Resource<Unit> =
        repository.subscribeTicker(receiveDelayMillis)
}