package com.mingg.domain.repository.ticker

import com.mingg.domain.model.ticker.Currency
import com.mingg.domain.model.ticker.SortModel
import com.mingg.domain.model.ticker.Ticker
import com.mingg.domain.model.ticker.TickerListModel
import com.mingg.domain.utils.Resource
import com.mingg.domain.utils.TickerResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface TickerRepository {
    val tickerSocketData: SharedFlow<TickerResource<TickerListModel>>

    val tickerSocketUnfilteredData: SharedFlow<TickerResource<TickerListModel>>

    suspend fun subscribeTicker(receiveDelayMillis: Long): Resource<Unit>

    suspend fun unsubscribeTicker()

    suspend fun sortTickerList(sortModel: SortModel)

    suspend fun searchTickerList(searchSymbol: String)

    suspend fun observeSingleTicker(
        symbol: String,
        currency: Currency
    ): Flow<Ticker>
}