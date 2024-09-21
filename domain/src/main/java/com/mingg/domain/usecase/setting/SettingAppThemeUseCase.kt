package com.mingg.domain.usecase.setting

import com.mingg.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SettingAppThemeUseCase @Inject constructor(private val repository: SettingRepository) {
    fun get() = repository.getSettingAppTheme()

    fun set(theme: String) = repository.setSettingAppTheme(theme)
}