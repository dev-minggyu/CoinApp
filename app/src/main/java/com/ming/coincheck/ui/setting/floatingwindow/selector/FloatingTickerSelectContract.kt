package com.ming.coincheck.ui.setting.floatingwindow.selector

import com.ming.coincheck.ui.base.UiEffect
import com.ming.coincheck.ui.base.UiIntent
import com.ming.coincheck.ui.base.UiState
import com.ming.domain.model.setting.FloatingTicker

sealed class FloatingTickerSelectIntent : UiIntent {
    data object LoadFloatingTickerList : FloatingTickerSelectIntent()
    data class SetFloatingTickerList(val list: List<String>) : FloatingTickerSelectIntent()
}

data class FloatingTickerSelectState(
    val tickerList: List<FloatingTicker>? = null
) : UiState

sealed class FloatingTickerSelectEffect : UiEffect