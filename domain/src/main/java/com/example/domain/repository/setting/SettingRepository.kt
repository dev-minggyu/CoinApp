package com.example.domain.repository.setting

interface SettingRepository {
    fun getSettingTickerChangeColor(): Boolean

    fun setSettingTickerChangeColor(value: Boolean)
}