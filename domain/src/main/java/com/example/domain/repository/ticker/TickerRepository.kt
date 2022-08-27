package com.example.domain.repository.ticker

import com.example.domain.utils.Resource

interface TickerRepository {
    suspend fun getAllTickers(): Resource<Unit>
}