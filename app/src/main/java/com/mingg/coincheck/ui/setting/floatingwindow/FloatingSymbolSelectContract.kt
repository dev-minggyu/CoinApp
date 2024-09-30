package com.mingg.coincheck.ui.setting.floatingwindow

import com.mingg.coincheck.ui.base.UiEffect
import com.mingg.coincheck.ui.base.UiIntent
import com.mingg.coincheck.ui.base.UiState
import com.mingg.domain.model.setting.FloatingTicker

sealed class FloatingSymbolSelectIntent : UiIntent {
    data object LoadFloatingSymbolList : FloatingSymbolSelectIntent()
    data class SetFloatingTickerList(val list: List<String>) : FloatingSymbolSelectIntent()
}

data class FloatingSymbolSelectState(
    val symbolList: List<FloatingTicker>? = null
) : UiState

sealed class FloatingSymbolSelectEffect : UiEffect