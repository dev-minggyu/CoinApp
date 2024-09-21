package com.mingg.coincheck.ui.setting

import androidx.lifecycle.ViewModel
import com.mingg.coincheck.utils.AppThemeManager
import com.mingg.domain.usecase.setting.SettingAppThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingAppThemeUseCase: SettingAppThemeUseCase
) : ViewModel() {
    fun getAppTheme() = settingAppThemeUseCase.get()

    fun setAppTheme(theme: String) {
        settingAppThemeUseCase.set(theme)
        AppThemeManager.applyTheme(theme)
    }
}