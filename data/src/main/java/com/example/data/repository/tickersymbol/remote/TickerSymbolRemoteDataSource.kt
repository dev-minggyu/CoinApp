package com.example.data.repository.tickersymbol.remote

import com.example.data.model.tickersymbol.TickerSymbolResponse
import com.example.domain.utils.Resource

interface TickerSymbolRemoteDataSource {
    suspend fun getTickerSymbolList(): Resource<List<TickerSymbolResponse>>
}