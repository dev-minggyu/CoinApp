package com.ming.coincheck.ui.splash

import androidx.lifecycle.viewModelScope
import com.ming.coincheck.ui.base.BaseViewModel
import com.ming.domain.usecase.setting.SettingFloatingWindowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    private val settingFloatingWindowUseCase: SettingFloatingWindowUseCase
) : BaseViewModel<InitState, InitIntent, InitEffect>() {

    override fun createInitialState(): InitState {
        return InitState()
    }

    init {
        setEvent(InitIntent.CheckFloatingWindowEnabled)
    }

    override fun handleEvent(event: InitIntent) {
        when (event) {
            InitIntent.CheckFloatingWindowEnabled -> checkFloatingWindowEnabled()
            InitIntent.DisableFloatingWindow -> disableFloatingWindow()
        }
    }

    private fun checkFloatingWindowEnabled() {
        viewModelScope.launch {
            val isEnabled = settingFloatingWindowUseCase.get()
            setState { copy(isFloatingWindowEnabled = isEnabled) }
        }
    }

    fun disableFloatingWindow() {
        viewModelScope.launch {
            settingFloatingWindowUseCase.set(false)
        }
    }
}