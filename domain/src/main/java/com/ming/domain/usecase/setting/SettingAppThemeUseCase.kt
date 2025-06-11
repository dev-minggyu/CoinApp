package com.ming.domain.usecase.setting

import com.ming.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SettingAppThemeUseCase @Inject constructor(private val repository: SettingRepository) {
    fun get() = repository.getSettingAppTheme()

    fun set(theme: String) = repository.setSettingAppTheme(theme)
}