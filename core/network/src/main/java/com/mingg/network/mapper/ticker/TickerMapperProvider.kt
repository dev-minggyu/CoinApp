package com.mingg.network.mapper.ticker

import com.mingg.domain.model.ticker.Ticker
import com.mingg.network.data.response.TickerResponse

interface TickerMapperProvider {
    fun mapperToTicker(tickerResponse: TickerResponse): Ticker
}