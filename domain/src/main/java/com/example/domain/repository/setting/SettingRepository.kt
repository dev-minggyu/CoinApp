package com.example.domain.repository.setting

interface SettingRepository {
    fun getSettingTickerChangeColor(): Boolean

    fun setSettingTickerChangeColor(value: Boolean)

    fun getSettingFloatingWindow(): Boolean

    fun setSettingFloatingTickerList(list: List<String>)

    fun getSettingFloatingTickerList(): List<String>
}