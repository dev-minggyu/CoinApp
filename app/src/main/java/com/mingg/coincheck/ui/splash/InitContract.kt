package com.mingg.coincheck.ui.splash

import com.mingg.coincheck.ui.base.UiEffect
import com.mingg.coincheck.ui.base.UiIntent
import com.mingg.coincheck.ui.base.UiState

sealed class InitIntent : UiIntent {
    data object CheckFloatingWindowEnabled : InitIntent()
    data object DisableFloatingWindow : InitIntent()
}

data class InitState(
    val isFloatingWindowEnabled: Boolean = false
) : UiState

sealed class InitEffect : UiEffect