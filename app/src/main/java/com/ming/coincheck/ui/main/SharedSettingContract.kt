package com.ming.coincheck.ui.main

import com.ming.coincheck.ui.base.UiEffect
import com.ming.coincheck.ui.base.UiIntent
import com.ming.coincheck.ui.base.UiState

sealed class SharedSettingIntent : UiIntent {
    data object LoadSettings : SharedSettingIntent()
    data class SetChangeTickerColor(val value: Boolean) : SharedSettingIntent()
}

data class SharedSettingState(
    val tickerChangeColor: Boolean = true
) : UiState

sealed class SharedSettingEffect : UiEffect