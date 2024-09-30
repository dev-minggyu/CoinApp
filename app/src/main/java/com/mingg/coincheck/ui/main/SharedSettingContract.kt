package com.mingg.coincheck.ui.main

import com.mingg.coincheck.ui.base.UiEffect
import com.mingg.coincheck.ui.base.UiIntent
import com.mingg.coincheck.ui.base.UiState

sealed class SharedSettingIntent : UiIntent {
    data object LoadSettings : SharedSettingIntent()
    data class SetChangeTickerColor(val value: Boolean) : SharedSettingIntent()
}

data class SharedSettingState(
    val tickerChangeColor: Boolean = true
) : UiState

sealed class SharedSettingEffect : UiEffect