package com.example.domain.repository.tickersymbol

import com.example.domain.utils.Resource

interface TickerSymbolRepository {
    suspend fun getTickerSymbolList(): Resource<Unit>
}