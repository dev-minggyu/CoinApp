package com.example.data.repository.setting.local;

interface SettingLocalDataSource {
    fun getSettingTickerChangeColor(): Boolean

    fun setSettingTickerChangeColor(value: Boolean)

    fun getSettingFloatingWindow(): Boolean

    fun setSettingFloatingTickerList(list: List<String>)

    fun getSettingFloatingTickerList(): List<String>
}
