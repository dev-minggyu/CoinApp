package com.example.coinapp.ui.setting

import android.content.ComponentName
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.os.Bundle
import android.os.IBinder
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.coinapp.R
import com.example.coinapp.ui.floating.FloatingWindowService
import com.example.coinapp.ui.floating.FloatingWindowServiceBinder
import com.example.coinapp.ui.main.MainSettingViewModel
import com.example.coinapp.utils.AppThemeManager

class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private val _settingViewModel: MainSettingViewModel by activityViewModels()

    private var _floatingWindowService: FloatingWindowService? = null

    private val _serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName?, binder: IBinder?) {
            _floatingWindowService = (binder as FloatingWindowServiceBinder).getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {}
    }

    private val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        when (result.resultCode) {
            REQUEST_CHECKED_FLOATING_SYMBOL -> {
                result.data?.getStringArrayListExtra(SettingFloatingSymbolActivity.KEY_CHECKED_FLOATING_SYMBOL_LIST)?.let {
                    _floatingWindowService?.setFloatingList(it.toList())
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setListener()
    }

    override fun onStart() {
        super.onStart()
        preferenceManager.sharedPreferences?.run {
            if (getBoolean(getString(R.string.key_pref_enable_floating_window), false)) {
                FloatingWindowService.startService(requireContext(), _serviceConnection)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        _floatingWindowService?.let {
            FloatingWindowService.unbindService(requireContext(), _serviceConnection)
        }
    }

    private fun setListener() {
        findPreference<Preference>(getString(R.string.settings_btn_select_ticker))?.setOnPreferenceClickListener {
            SettingFloatingSymbolActivity.createIntent(requireContext()).also {
                activityResult.launch(it)
            }
            return@setOnPreferenceClickListener true
        }
    }

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

    override fun onSharedPreferenceChanged(pref: SharedPreferences, key: String?) {
        when (key) {
            getString(R.string.key_pref_app_theme) -> {
                val theme = pref.getString(key, AppThemeManager.THEME_SYSTEM)!!
                AppThemeManager.applyTheme(theme)
            }
            getString(R.string.key_pref_ticker_change_color) -> {
                val tickerChangeColor = pref.getBoolean(key, true)
                _settingViewModel.setTickerChangeColor(tickerChangeColor)
            }
            getString(R.string.key_pref_enable_floating_window) -> {
                if (pref.getBoolean(key, false)) {
                    FloatingWindowService.startService(requireContext(), _serviceConnection)
                } else {
                    _floatingWindowService?.let {
                        _floatingWindowService?.stopService(requireContext(), _serviceConnection)
                        _floatingWindowService = null
                    }
                }
            }
        }
    }

    companion object {
        const val REQUEST_CHECKED_FLOATING_SYMBOL = 0

        fun newInstance() = SettingFragment()
    }
}