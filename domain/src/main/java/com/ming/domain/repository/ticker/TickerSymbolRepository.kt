package com.ming.domain.repository.ticker

import com.ming.domain.utils.Resource

interface TickerSymbolRepository {
    suspend fun getTickerSymbolList(): Resource<Unit>
}