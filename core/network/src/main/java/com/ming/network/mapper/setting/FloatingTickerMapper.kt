package com.ming.network.mapper.setting

import com.ming.domain.model.setting.FloatingTicker
import com.ming.domain.model.ticker.TickerSymbol

object FloatingTickerMapper {
    fun fromTickerSymbol(tickerSymbol: TickerSymbol): FloatingTicker =
        FloatingTicker(
            symbol = tickerSymbol.symbol + tickerSymbol.currency.name,
            koreanSymbol = tickerSymbol.koreanSymbol,
            englishSymbol = tickerSymbol.englishSymbol,
            isChecked = false
        )
}