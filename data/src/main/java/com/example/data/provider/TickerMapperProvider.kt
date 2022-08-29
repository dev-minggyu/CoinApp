package com.example.data.provider

import com.example.data.model.ticker.TickerResponse
import com.example.domain.model.ticker.Ticker

interface TickerMapperProvider {
    fun mapperToTicker(tickerResponse: TickerResponse): Ticker
}