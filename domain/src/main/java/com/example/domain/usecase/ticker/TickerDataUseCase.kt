package com.example.domain.usecase.ticker

import com.example.domain.model.ticker.TickerListModel
import com.example.domain.repository.ticker.TickerRepository
import com.example.domain.utils.TickerResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TickerDataUseCase @Inject constructor(private val repository: TickerRepository) {
    fun execute(): Flow<TickerResource<TickerListModel>> = repository.tickerSocketData
}