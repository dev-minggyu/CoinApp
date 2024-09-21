package com.mingg.domain.usecase.setting

import com.mingg.domain.repository.setting.SettingRepository
import javax.inject.Inject

class AllFloatingTickerListUseCase @Inject constructor(private val repository: SettingRepository) {
    suspend fun execute() = repository.getFloatingTickerList()
}