package com.ming.network.mapper.ticker

import com.ming.domain.model.ticker.Ticker
import com.ming.network.data.response.TickerResponse

interface TickerResponseMapper {
    fun mapperToTicker(tickerResponse: TickerResponse): Ticker
}