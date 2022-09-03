package com.example.data.repository.setting

import com.example.data.repository.setting.local.SettingLocalDataSourceImpl
import com.example.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingLocalDataSourceImpl: SettingLocalDataSourceImpl
) : SettingRepository {
    override fun getSettingTickerChangeColor(): Boolean = settingLocalDataSourceImpl.getSettingTickerChangeColor()

    override fun setSettingTickerChangeColor(value: Boolean) {
        settingLocalDataSourceImpl.setSettingTickerChangeColor(value)
    }
}