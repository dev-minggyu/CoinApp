package com.example.coinapp.ui.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.coinapp.R
import com.example.coinapp.databinding.FragmentSettingBinding
import com.example.coinapp.extension.showThemeDialog
import com.example.coinapp.ui.base.BaseFragment
import com.example.coinapp.ui.main.ShareSettingViewModel
import com.example.coinapp.ui.setting.floatingwindow.FloatingWindowSettingActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    private val _settingViewModel: SettingViewModel by viewModels()

    private val _shareSettingViewModel: ShareSettingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this

        loadSettings()
    }

    private fun loadSettings() {
        binding.switchChangeTickerColor.isChecked = _shareSettingViewModel.getChangeTickerColor()
    }

    fun settingMenuClick(id: Int) {
        when (id) {
            R.id.tv_theme -> {
                showThemeDialog(_settingViewModel.getAppTheme()) { theme ->
                    _settingViewModel.setAppTheme(theme)
                }
            }

            R.id.tv_floating_window_setting -> {
                FloatingWindowSettingActivity.startActivity(requireContext())
            }
        }
    }

    fun settingSwitchClick(id: Int, isChecked: Boolean) {
        when (id) {
            R.id.switch_change_ticker_color -> {
                _shareSettingViewModel.setChangeTickerColor(isChecked)
            }
        }
    }

    companion object {
        fun newInstance() = SettingFragment()
    }
}