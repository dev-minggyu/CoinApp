package com.ming.domain.usecase.setting

import com.ming.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SettingTickerChangeColorUseCase @Inject constructor(private val repository: SettingRepository) {
    fun get() = repository.getSettingTickerChangeColor()

    fun set(value: Boolean) {
        repository.setSettingTickerChangeColor(value)
    }
}