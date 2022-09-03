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

    companion object {
        private const val KEY_TICKER_CHANGE_COLOR = "KEY_TICKER_CHANGE_COLOR"
    }
}
