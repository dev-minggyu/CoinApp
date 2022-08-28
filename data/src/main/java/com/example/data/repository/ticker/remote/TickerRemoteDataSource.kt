package com.example.data.repository.ticker.remote

import com.example.data.model.ticker.TickerResponse
import kotlinx.coroutines.flow.Flow

interface TickerRemoteDataSource {
    fun observeTickerList(): Flow<List<TickerResponse>>
}