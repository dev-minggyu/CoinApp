package com.ming.network.ticker

import com.ming.domain.utils.Resource
import com.ming.network.data.response.TickerSymbolResponse

interface TickerSymbolRemoteDataSource {
    suspend fun getTickerSymbolList(): Resource<List<TickerSymbolResponse>>
}