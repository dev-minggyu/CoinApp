package com.mingg.coincheck.ui.main

import com.mingg.coincheck.ui.base.UiEffect
import com.mingg.coincheck.ui.base.UiIntent
import com.mingg.coincheck.ui.base.UiState

sealed class ShareSettingIntent : UiIntent {
    data object LoadSettings : ShareSettingIntent()
    data class SetChangeTickerColor(val value: Boolean) : ShareSettingIntent()
}

data class ShareSettingState(
    val tickerChangeColor: Boolean = true
) : UiState

sealed class ShareSettingEffect : UiEffect