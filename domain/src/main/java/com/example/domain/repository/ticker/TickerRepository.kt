package com.example.domain.repository.ticker

import com.example.domain.model.ticker.SortModel
import com.example.domain.model.ticker.Ticker
import com.example.domain.utils.Resource
import com.example.domain.utils.TickerResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface TickerRepository {
    val tickerSocketData: SharedFlow<TickerResource<List<Ticker>>>

    suspend fun subscribeTicker(): Resource<Unit>

    suspend fun unsubscribeTicker()

    suspend fun sortTickerList(sortModel: SortModel)

    suspend fun searchTickerList(searchSymbol: String)
}