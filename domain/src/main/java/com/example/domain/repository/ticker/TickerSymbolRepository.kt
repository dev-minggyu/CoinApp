package com.example.domain.repository.ticker

import com.example.domain.utils.Resource

interface TickerSymbolRepository {
    suspend fun getTickerSymbolList(): Resource<Unit>
}