package com.example.data.repository.ticker.remote

import com.example.data.model.ticker.TickerSymbolResponse
import com.example.domain.utils.Resource

interface TickerSymbolRemoteDataSource {
    suspend fun getTickerSymbolList(): Resource<List<TickerSymbolResponse>>
}