package com.mingg.domain.usecase.setting

import com.mingg.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SettingFloatingTickerListUseCase @Inject constructor(private val repository: SettingRepository) {
    fun get() = repository.getSettingFloatingTickerList()

    fun set(list: List<String>) = repository.setSettingFloatingTickerList(list)
}