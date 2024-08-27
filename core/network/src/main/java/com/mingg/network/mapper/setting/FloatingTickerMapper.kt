package com.mingg.network.mapper.setting

import com.mingg.domain.model.setting.FloatingTicker
import com.mingg.domain.model.ticker.TickerSymbol

object FloatingTickerMapper {
    fun fromTickerSymbol(tickerSymbol: TickerSymbol): FloatingTicker =
        FloatingTicker(
            symbol = tickerSymbol.symbol + tickerSymbol.currency.name,
            koreanSymbol = tickerSymbol.koreanSymbol,
            englishSymbol = tickerSymbol.englishSymbol,
            isChecked = false
        )
}