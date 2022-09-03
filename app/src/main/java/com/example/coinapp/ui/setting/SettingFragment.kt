package com.example.coinapp.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceFragmentCompat
import com.example.coinapp.R
import com.example.coinapp.ui.main.MainSettingViewModel
import com.example.coinapp.utils.AppThemeManager

class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private val _settingViewModel: MainSettingViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_settings, rootKey)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(pref: SharedPreferences?, key: String?) {
        when (key) {
            getString(R.string.key_pref_app_theme) -> {
                val theme = pref?.getString(key, AppThemeManager.THEME_SYSTEM)!!
                AppThemeManager.applyTheme(theme)
            }
            getString(R.string.key_pref_ticker_change_color) -> {
                val tickerChangeColor = pref?.getBoolean(key, true)!!
                _settingViewModel.setTickerChangeColor(tickerChangeColor)
            }
        }
    }

    companion object {
        fun newInstance() = SettingFragment()
    }
}