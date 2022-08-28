package com.example.data.mapper

import com.example.data.model.ticker.TickerResponse
import com.example.domain.model.ticker.Ticker

object TickerMapper {
    fun mapperToTicker(tickerResponse: TickerResponse): Ticker {
        return Ticker(
            symbol = tickerResponse.code,
            currencyType = tickerResponse.code.split("-")[0],
            currentPrice = tickerResponse.trade_price,
            prevPrice = tickerResponse.prev_closing_price,
            rate = ((tickerResponse.trade_price - tickerResponse.prev_closing_price) / tickerResponse.prev_closing_price) / 100,
            volume = tickerResponse.trade_volume
        )
    }
}