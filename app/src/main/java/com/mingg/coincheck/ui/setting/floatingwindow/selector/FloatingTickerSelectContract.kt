package com.mingg.coincheck.ui.setting.floatingwindow.selector

import com.mingg.coincheck.ui.base.UiEffect
import com.mingg.coincheck.ui.base.UiIntent
import com.mingg.coincheck.ui.base.UiState
import com.mingg.domain.model.setting.FloatingTicker

sealed class FloatingTickerSelectIntent : UiIntent {
    data object LoadFloatingTickerList : FloatingTickerSelectIntent()
    data class SetFloatingTickerList(val list: List<String>) : FloatingTickerSelectIntent()
}

data class FloatingTickerSelectState(
    val tickerList: List<FloatingTicker>? = null
) : UiState

sealed class FloatingTickerSelectEffect : UiEffect