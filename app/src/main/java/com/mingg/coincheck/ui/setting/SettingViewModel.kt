package com.mingg.coincheck.ui.setting

import androidx.lifecycle.viewModelScope
import com.mingg.coincheck.ui.base.BaseViewModel
import com.mingg.domain.usecase.setting.SettingAppThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingAppThemeUseCase: SettingAppThemeUseCase
) : BaseViewModel<SettingState, SettingIntent, SettingEffect>() {

    override fun createInitialState(): SettingState {
        return SettingState()
    }

    override fun handleEvent(event: SettingIntent) {
        when (event) {
            is SettingIntent.GetAppTheme -> getAppTheme()
            is SettingIntent.SetAppTheme -> setAppTheme(event.theme)
        }
    }

    private fun getAppTheme() {
        viewModelScope.launch {
            val currentTheme = settingAppThemeUseCase.get()
            setEffect { SettingEffect.ShowThemeDialog(currentTheme) }
        }
    }

    private fun setAppTheme(theme: String) {
        viewModelScope.launch {
            settingAppThemeUseCase.set(theme)
            setState { copy(theme = theme) }
            setEffect { SettingEffect.ApplyTheme(theme) }
        }
    }
}