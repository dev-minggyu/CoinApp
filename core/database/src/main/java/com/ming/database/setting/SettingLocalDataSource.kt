package com.ming.database.setting

interface SettingLocalDataSource {
    fun getSettingTickerChangeColor(): Boolean

    fun setSettingTickerChangeColor(value: Boolean)

    fun getSettingFloatingWindow(): Boolean

    fun setSettingFloatingWindow(isEnable: Boolean)

    fun setSettingFloatingTickerList(list: List<String>)

    fun getSettingFloatingTickerList(): List<String>

    fun getSettingFloatingTransparent(): Int

    fun setSettingFloatingTransparent(value: Int)

    fun getSettingAppTheme(): String

    fun setSettingAppTheme(theme: String)
}
