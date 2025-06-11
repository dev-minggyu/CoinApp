package com.ming.domain.usecase.setting

import com.ming.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SettingFloatingTransparentUseCase @Inject constructor(private val repository: SettingRepository) {
    fun get() = repository.getSettingFloatingTransparent()

    fun set(value: Int) = repository.setSettingFloatingTransparent(value)
}