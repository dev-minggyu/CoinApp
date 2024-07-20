package com.example.database.setting;

import android.content.SharedPreferences
import javax.inject.Inject

class SettingLocalDataSourceImpl @Inject constructor(
    private val preferences: SharedPreferences
) : SettingLocalDataSource {
    override fun getSettingTickerChangeColor(): Boolean =
        preferences.getBoolean(KEY_TICKER_CHANGE_COLOR, true)

    override fun setSettingTickerChangeColor(value: Boolean) {
        preferences.edit().putBoolean(KEY_TICKER_CHANGE_COLOR, value).commit()
    }

    override fun getSettingFloatingWindow(): Boolean =
        preferences.getBoolean(KEY_PREF_ENABLE_FLOATING_WINDOW, false)

    override fun setSettingFloatingWindow(isEnable: Boolean) {
        preferences.edit().putBoolean(KEY_PREF_ENABLE_FLOATING_WINDOW, isEnable).commit()
    }

    override fun setSettingFloatingTickerList(list: List<String>) {
        preferences.edit().putStringSet(KEY_PREF_FLOATING_TICKER_SET, list.toSet()).commit()
    }

    override fun getSettingFloatingTickerList(): List<String> =
        preferences.getStringSet(KEY_PREF_FLOATING_TICKER_SET, setOf())!!.toList()

    override fun getSettingFloatingTransparent(): Int =
        preferences.getInt(KEY_PREF_FLOATING_WINDOW_TRANSPARENT, 127)

    override fun setSettingFloatingTransparent(value: Int) {
        preferences.edit().putInt(KEY_PREF_FLOATING_WINDOW_TRANSPARENT, value).commit()
    }

    override fun getSettingAppTheme(): String = preferences.getString(KEY_APP_THEME, "system")!!

    override fun setSettingAppTheme(theme: String) {
        preferences.edit().putString(KEY_APP_THEME, theme).commit()
    }

    companion object {
        private const val KEY_TICKER_CHANGE_COLOR = "KEY_TICKER_CHANGE_COLOR"
        private const val KEY_PREF_ENABLE_FLOATING_WINDOW = "KEY_PREF_ENABLE_FLOATING_WINDOW"
        private const val KEY_PREF_FLOATING_TICKER_SET = "KEY_PREF_FLOATING_TICKER_SET"
        private const val KEY_PREF_FLOATING_WINDOW_TRANSPARENT =
            "KEY_PREF_FLOATING_WINDOW_TRANSPARENT"
        private const val KEY_APP_THEME = "KEY_APP_THEME"
    }
}
