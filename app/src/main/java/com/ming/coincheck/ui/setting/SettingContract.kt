package com.ming.coincheck.ui.setting

import com.ming.coincheck.ui.base.UiEffect
import com.ming.coincheck.ui.base.UiIntent
import com.ming.coincheck.ui.base.UiState

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