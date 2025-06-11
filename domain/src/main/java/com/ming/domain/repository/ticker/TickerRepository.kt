package com.ming.domain.repository.ticker

import com.ming.domain.model.ticker.Currency
import com.ming.domain.model.ticker.SortModel
import com.ming.domain.model.ticker.Ticker
import com.ming.domain.model.ticker.TickerListModel
import com.ming.domain.utils.Resource
import com.ming.domain.utils.TickerResource
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