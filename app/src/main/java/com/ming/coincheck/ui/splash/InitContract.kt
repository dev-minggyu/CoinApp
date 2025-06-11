package com.ming.coincheck.ui.splash

import com.ming.coincheck.ui.base.UiEffect
import com.ming.coincheck.ui.base.UiIntent
import com.ming.coincheck.ui.base.UiState

sealed class InitIntent : UiIntent {
    data object CheckFloatingWindowEnabled : InitIntent()
    data object DisableFloatingWindow : InitIntent()
}

data class InitState(
    val isFloatingWindowEnabled: Boolean = false
) : UiState

sealed class InitEffect : UiEffect