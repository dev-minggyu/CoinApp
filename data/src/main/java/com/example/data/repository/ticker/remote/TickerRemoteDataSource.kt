package com.example.data.repository.ticker.remote

import com.example.data.model.ticker.TickerResponse
import com.example.domain.utils.Resource

interface TickerRemoteDataSource {
    suspend fun getAllTickers(): Resource<List<TickerResponse>>
}