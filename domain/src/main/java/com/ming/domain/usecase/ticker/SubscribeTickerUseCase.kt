package com.ming.domain.usecase.ticker

import com.ming.domain.repository.ticker.TickerRepository
import com.ming.domain.utils.Resource
import javax.inject.Inject

class SubscribeTickerUseCase @Inject constructor(private val repository: TickerRepository) {
    suspend fun execute(receiveDelayMillis: Long = 300L): Resource<Unit> =
        repository.subscribeTicker(receiveDelayMillis)
}