package com.mingg.coincheck.ui.tickerdetail

import com.mingg.coincheck.ui.base.UiEffect
import com.mingg.coincheck.ui.base.UiIntent
import com.mingg.coincheck.ui.base.UiState
import com.mingg.domain.model.ticker.Currency
import com.mingg.domain.model.ticker.Ticker

sealed class TickerDetailIntent : UiIntent {
    data class LoadTicker(val symbol: String, val currency: Currency) : TickerDetailIntent()
}

data class TickerDetailState(
    val ticker: Ticker? = null
) : UiState

sealed class TickerDetailEffect : UiEffect