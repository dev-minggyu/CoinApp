package com.ming.coincheck.ui.setting.floatingwindow.setting

import androidx.lifecycle.viewModelScope
import com.ming.coincheck.ui.base.BaseViewModel
import com.ming.domain.usecase.setting.SettingFloatingTransparentUseCase
import com.ming.domain.usecase.setting.SettingFloatingWindowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FloatingWindowSettingViewModel @Inject constructor(
    private val settingFloatingWindowUseCase: SettingFloatingWindowUseCase,
    private val settingFloatingTransparentUseCase: SettingFloatingTransparentUseCase
) : BaseViewModel<FloatingWindowSettingState, FloatingWindowSettingIntent, FloatingWindowSettingEffect>() {

    override fun createInitialState(): FloatingWindowSettingState {
        return FloatingWindowSettingState()
    }

    override fun handleEvent(event: FloatingWindowSettingIntent) {
        when (event) {
            is FloatingWindowSettingIntent.LoadSettings -> loadSettings()
            is FloatingWindowSettingIntent.SetEnableFloatingWindow -> setEnableFloatingWindow(event.isEnabled)
            is FloatingWindowSettingIntent.SetFloatingWindowTransparency -> setFloatingWindowTransparency(event.transparency)
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            val isEnabled = settingFloatingWindowUseCase.get()
            val transparency = settingFloatingTransparentUseCase.get()
            setState {
                copy(
                    isFloatingWindowEnabled = isEnabled,
                    floatingWindowTransparency = transparency
                )
            }
        }
    }

    private fun setEnableFloatingWindow(isEnabled: Boolean) {
        viewModelScope.launch {
            settingFloatingWindowUseCase.set(isEnabled)
            setState { copy(isFloatingWindowEnabled = isEnabled) }
        }
    }

    private fun setFloatingWindowTransparency(transparency: Int) {
        viewModelScope.launch {
            settingFloatingTransparentUseCase.set(transparency)
            setState { copy(floatingWindowTransparency = transparency) }
        }
    }
}