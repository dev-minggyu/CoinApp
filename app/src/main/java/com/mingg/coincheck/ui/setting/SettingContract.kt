package com.mingg.coincheck.ui.setting

import com.mingg.coincheck.ui.base.UiEffect
import com.mingg.coincheck.ui.base.UiIntent
import com.mingg.coincheck.ui.base.UiState

sealed class SettingIntent : UiIntent {
    data object GetAppTheme : SettingIntent()
    data class SetAppTheme(val theme: String) : SettingIntent()
}

data class SettingState(
    val theme: String = "system"
) : UiState

sealed class SettingEffect : UiEffect {
    data class ApplyTheme(val theme: String) : SettingEffect()
    data class ShowThemeDialog(val currentTheme: String) : SettingEffect()
}