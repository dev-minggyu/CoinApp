package com.example.domain.repository.ticker

import com.example.domain.model.ticker.Ticker
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TickerRepository {
    fun observeTicker(): Flow<Resource<List<Ticker>>>
}