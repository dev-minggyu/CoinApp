package com.example.domain.repository.ticker

import com.example.domain.model.ticker.*
import com.example.domain.utils.Resource
import com.example.domain.utils.TickerResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface TickerRepository {
    val tickerSocketData: SharedFlow<TickerResource<TickerListModel>>

    suspend fun subscribeTicker(receiveDelayMillis: Long): Resource<Unit>

    suspend fun unsubscribeTicker()

    suspend fun sortTickerList(sortModel: SortModel)

    suspend fun searchTickerList(searchSymbol: String)

    suspend fun observeSingleTicker(symbol: String, currency: Currency): Flow<Ticker>

    suspend fun getSymbolList(): List<FloatingTicker>
}