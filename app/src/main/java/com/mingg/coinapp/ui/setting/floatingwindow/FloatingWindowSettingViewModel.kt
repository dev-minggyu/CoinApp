package com.mingg.coinapp.ui.setting.floatingwindow

import androidx.lifecycle.ViewModel
import com.mingg.domain.usecase.setting.SettingFloatingTransparentUseCase
import com.mingg.domain.usecase.setting.SettingFloatingWindowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FloatingWindowSettingViewModel @Inject constructor(
    private val _settingFloatingWindowUseCase: SettingFloatingWindowUseCase,
    private val _settingFloatingTransparentUseCase: SettingFloatingTransparentUseCase
) : ViewModel() {

    fun isEnableFloatingWindow() = _settingFloatingWindowUseCase.get()

    fun setEnableFloatingWindow(isEnable: Boolean) = _settingFloatingWindowUseCase.set(isEnable)

    fun getFloatingWindowTransparent() = _settingFloatingTransparentUseCase.get()

    fun setFloatingWindowTransparent(value: Int) = _settingFloatingTransparentUseCase.set(value)
}