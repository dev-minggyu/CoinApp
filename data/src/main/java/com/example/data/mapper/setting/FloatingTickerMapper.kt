package com.example.data.mapper.setting

import com.example.domain.model.setting.FloatingTicker
import com.example.domain.model.ticker.TickerSymbol

object FloatingTickerMapper {
    fun fromTickerSymbol(tickerSymbol: TickerSymbol): FloatingTicker =
        FloatingTicker(
            symbol = tickerSymbol.symbol + tickerSymbol.currency.name,
            koreanSymbol = tickerSymbol.koreanSymbol,
            englishSymbol = tickerSymbol.englishSymbol,
            isChecked = false
        )
}