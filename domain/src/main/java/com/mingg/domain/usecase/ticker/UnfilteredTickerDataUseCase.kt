package com.mingg.domain.usecase.ticker

import com.mingg.domain.model.ticker.TickerListModel
import com.mingg.domain.repository.ticker.TickerRepository
import com.mingg.domain.utils.TickerResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnfilteredTickerDataUseCase @Inject constructor(private val repository: TickerRepository) {
    fun execute(): Flow<TickerResource<TickerListModel>> =
        repository.tickerSocketUnfilteredData
}