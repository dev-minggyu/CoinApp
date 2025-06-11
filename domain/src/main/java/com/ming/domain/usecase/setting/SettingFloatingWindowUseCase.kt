package com.ming.domain.usecase.setting

import com.ming.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SettingFloatingWindowUseCase @Inject constructor(private val repository: SettingRepository) {
    fun get() = repository.getSettingFloatingWindow()

    fun set(isEnable: Boolean) = repository.setSettingFloatingWindow(isEnable)
}