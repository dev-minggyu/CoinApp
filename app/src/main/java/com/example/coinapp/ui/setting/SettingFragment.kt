package com.example.coinapp.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.coinapp.R
import com.example.coinapp.utils.AppThemeManager

class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
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
        }
    }

    companion object {
        fun newInstance() = SettingFragment()
    }
}