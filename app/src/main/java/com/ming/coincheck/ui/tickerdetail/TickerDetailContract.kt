package com.ming.coincheck.ui.tickerdetail

import com.ming.coincheck.ui.base.UiEffect
import com.ming.coincheck.ui.base.UiIntent
import com.ming.coincheck.ui.base.UiState
import com.ming.domain.model.ticker.Currency
import com.ming.domain.model.ticker.Ticker

sealed class TickerDetailIntent : UiIntent {
    data class LoadTicker(val symbol: String, val currency: Currency) : TickerDetailIntent()
}

data class TickerDetailState(
    val ticker: Ticker? = null
) : UiState

sealed class TickerDetailEffect : UiEffect