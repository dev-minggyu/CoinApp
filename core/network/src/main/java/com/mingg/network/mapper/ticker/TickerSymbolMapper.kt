package com.mingg.network.mapper.ticker

import com.mingg.domain.model.ticker.Currency
import com.mingg.domain.model.ticker.Ticker
import com.mingg.network.data.response.TickerSymbolResponse

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