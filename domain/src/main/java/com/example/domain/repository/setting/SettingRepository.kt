package com.example.domain.repository.setting

import com.example.domain.model.setting.FloatingTicker

interface SettingRepository {
    fun getSettingTickerChangeColor(): Boolean

    fun setSettingTickerChangeColor(value: Boolean)

    fun getSettingFloatingWindow(): Boolean

    fun setSettingFloatingTickerList(list: List<String>)

    fun getSettingFloatingTickerList(): List<String>

    suspend fun getFloatingTickerList(): List<FloatingTicker>

    fun getSettingFloatingTransparent(): Int
}