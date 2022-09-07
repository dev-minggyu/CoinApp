package com.example.data.repository.setting.local;

import android.content.SharedPreferences
import javax.inject.Inject

class SettingLocalDataSourceImpl @Inject constructor(
    private val preferences: SharedPreferences
) : SettingLocalDataSource {
    override fun getSettingTickerChangeColor(): Boolean = preferences.getBoolean(KEY_TICKER_CHANGE_COLOR, true)

    override fun setSettingTickerChangeColor(value: Boolean) {
        preferences.edit().putBoolean(KEY_TICKER_CHANGE_COLOR, value).commit()
    }

    override fun getSettingFloatingWindow(): Boolean = preferences.getBoolean(KEY_PREF_ENABLE_FLOATING_WINDOW, false)

    override fun setSettingFloatingTickerList(list: List<String>) {
        preferences.edit().putStringSet(KEY_PREF_FLOATING_TICKER_SET, list.toSet()).commit()
    }

    override fun getSettingFloatingTickerList(): List<String> = preferences.getStringSet(KEY_PREF_FLOATING_TICKER_SET, setOf())!!.toList()

    companion object {
        private const val KEY_TICKER_CHANGE_COLOR = "KEY_TICKER_CHANGE_COLOR"
        private const val KEY_PREF_ENABLE_FLOATING_WINDOW = "KEY_PREF_ENABLE_FLOATING_WINDOW"
        private const val KEY_PREF_FLOATING_TICKER_SET = "KEY_PREF_FLOATING_TICKER_SET"
    }
}
