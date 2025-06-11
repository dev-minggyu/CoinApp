package com.ming.network.mapper.ticker

import com.ming.domain.model.ticker.Currency
import com.ming.domain.model.ticker.Ticker
import com.ming.network.data.response.TickerSymbolResponse

object TickerSymbolMapper {
    fun toTicker(tickerSymbolResponse: TickerSymbolResponse): Ticker {
        val splitMarket = tickerSymbolResponse.market.split("-")
        return Ticker(
            splitMarket[1],
            tickerSymbolResponse.koreanName,
            tickerSymbolResponse.englishName,
            Currency.valueOf(splitMarket[0]),
            "",
            "",
            "",
            "",
            "",
            ""
        )
    }
}