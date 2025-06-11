package com.ming.domain.usecase.setting

import com.ming.domain.repository.setting.SettingRepository
import javax.inject.Inject

class AllFloatingTickerListUseCase @Inject constructor(private val repository: SettingRepository) {
    suspend fun execute() = repository.getFloatingTickerList()
}