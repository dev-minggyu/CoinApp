package com.mingg.domain.repository.ticker

import com.mingg.domain.utils.Resource

interface TickerSymbolRepository {
    suspend fun getTickerSymbolList(): Resource<Unit>
}