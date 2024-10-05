package com.mingg.coincheck.ui.setting.floatingwindow.setting

import com.mingg.coincheck.ui.base.UiEffect
import com.mingg.coincheck.ui.base.UiIntent
import com.mingg.coincheck.ui.base.UiState

sealed class FloatingWindowSettingIntent : UiIntent {
    data object LoadSettings : FloatingWindowSettingIntent()
    data class SetEnableFloatingWindow(val isEnabled: Boolean) : FloatingWindowSettingIntent()
    data class SetFloatingWindowTransparency(val transparency: Int) : FloatingWindowSettingIntent()
}

data class FloatingWindowSettingState(
    val isFloatingWindowEnabled: Boolean = false,
    val floatingWindowTransparency: Int = 0
) : UiState

sealed class FloatingWindowSettingEffect : UiEffect