package com.mingg.network.ticker

import com.mingg.domain.utils.Resource
import com.mingg.network.data.response.TickerSymbolResponse

interface TickerSymbolRemoteDataSource {
    suspend fun getTickerSymbolList(): Resource<List<TickerSymbolResponse>>
}