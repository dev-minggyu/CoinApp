package com.example.network.mapper.ticker

import com.example.domain.model.ticker.Ticker
import com.example.network.data.response.TickerResponse

interface TickerMapperProvider {
    fun mapperToTicker(tickerResponse: TickerResponse): Ticker
}