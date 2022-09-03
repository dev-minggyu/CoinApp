package com.example.data.mapper.ticker

import com.example.data.model.ticker.TickerResponse
import com.example.domain.model.ticker.Ticker

interface TickerMapperProvider {
    fun mapperToTicker(tickerResponse: TickerResponse): Ticker
}