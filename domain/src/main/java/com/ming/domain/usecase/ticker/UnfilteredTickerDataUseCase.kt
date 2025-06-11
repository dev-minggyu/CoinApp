package com.ming.domain.usecase.ticker

import com.ming.domain.model.ticker.TickerListModel
import com.ming.domain.repository.ticker.TickerRepository
import com.ming.domain.utils.TickerResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnfilteredTickerDataUseCase @Inject constructor(private val repository: TickerRepository) {
    fun execute(): Flow<TickerResource<TickerListModel>> =
        repository.tickerSocketUnfilteredData
}