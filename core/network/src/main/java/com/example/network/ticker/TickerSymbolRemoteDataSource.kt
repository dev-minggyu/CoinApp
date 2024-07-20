package com.example.network.ticker

import com.example.domain.utils.Resource
import com.example.network.data.response.TickerSymbolResponse

interface TickerSymbolRemoteDataSource {
    suspend fun getTickerSymbolList(): Resource<List<TickerSymbolResponse>>
}