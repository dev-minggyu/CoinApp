package com.example.data.mapper.ticker

import com.example.data.model.ticker.TickerSymbolResponse
import com.example.domain.model.ticker.Currency
import com.example.domain.model.ticker.Ticker
import com.example.domain.model.ticker.TickerSymbol

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

    fun fromTicker(ticker: Ticker): TickerSymbol =
        TickerSymbol(
            symbol = ticker.symbol,
            currency = ticker.currencyType,
            koreanSymbol = ticker.koreanSymbol,
            englishSymbol = ticker.englishSymbol
        )
}