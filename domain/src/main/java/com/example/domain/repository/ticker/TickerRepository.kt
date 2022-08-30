package com.example.domain.repository.ticker

import com.example.domain.model.ticker.SortModel
import com.example.domain.model.ticker.Ticker
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.SharedFlow

interface TickerRepository {
    val tickerSocketData: SharedFlow<Resource<List<Ticker>>>

    suspend fun subscribeTicker(): Resource<Unit>

    suspend fun unsubscribeTicker()

    suspend fun getSortedTickerList(sortModel: SortModel): List<Ticker>
}