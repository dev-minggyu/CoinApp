package com.example.domain.usecase.setting

import com.example.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SettingAppThemeUseCase @Inject constructor(private val repository: SettingRepository) {
    fun get() = repository.getSettingAppTheme()

    fun set(theme: String) = repository.setSettingAppTheme(theme)
}