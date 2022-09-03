package com.example.data.repository.setting.local;

interface SettingLocalDataSource {
    fun getSettingTickerChangeColor(): Boolean

    fun setSettingTickerChangeColor(value: Boolean)
}
