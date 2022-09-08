package com.example.coinapp.ui.splash

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.setting.SettingFloatingWindowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    private val settingFloatingWindowUseCase: SettingFloatingWindowUseCase
) : ViewModel() {
    private val _isEnableFloatingWindow = MutableStateFlow(false)
    val isEnableFloatingWindow = _isEnableFloatingWindow.asStateFlow()

    init {
        isEnabledFloatingWindow()
    }

    private fun isEnabledFloatingWindow() {
        _isEnableFloatingWindow.value = settingFloatingWindowUseCase.get()
    }

    fun disableFloatingWindow() {
        settingFloatingWindowUseCase.set(false)
    }
}