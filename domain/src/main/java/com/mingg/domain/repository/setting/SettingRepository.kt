package com.mingg.domain.repository.setting

import com.mingg.domain.model.setting.FloatingTicker

interface SettingRepository {
    fun getSettingTickerChangeColor(): Boolean

    fun setSettingTickerChangeColor(value: Boolean)

    fun getSettingFloatingWindow(): Boolean

    fun setSettingFloatingWindow(isEnable: Boolean)

    fun setSettingFloatingTickerList(list: List<String>)

    fun getSettingFloatingTickerList(): List<String>

    suspend fun getFloatingTickerList(): List<FloatingTicker>

    fun getSettingFloatingTransparent(): Int

    fun setSettingFloatingTransparent(value: Int)

    fun getSettingAppTheme(): String

    fun setSettingAppTheme(theme: String)
}