package com.mingg.coincheck.ui.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mingg.coincheck.R
import com.mingg.coincheck.databinding.FragmentSettingBinding
import com.mingg.coincheck.extension.collectWithLifecycle
import com.mingg.coincheck.extension.showThemeDialog
import com.mingg.coincheck.ui.base.BaseFragment
import com.mingg.coincheck.ui.main.ShareSettingIntent
import com.mingg.coincheck.ui.main.ShareSettingViewModel
import com.mingg.coincheck.ui.setting.floatingwindow.FloatingWindowSettingActivity
import com.mingg.coincheck.utils.AppThemeManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    private val _settingViewModel: SettingViewModel by viewModels()
    private val _shareSettingViewModel: ShareSettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        setupObservers()
    }

    private fun setupListener() {
        with(binding) {
            tvTheme.setOnClickListener {
                _settingViewModel.setEvent(SettingIntent.GetAppTheme)
            }
            switchChangeTickerColor.setOnCheckedChangeListener { _, isChecked ->
                _shareSettingViewModel.setEvent(ShareSettingIntent.SetChangeTickerColor(isChecked))
            }
            tvFloatingWindowSetting.setOnClickListener {
                FloatingWindowSettingActivity.startActivity(requireContext())
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            _settingViewModel.effect.collect { effect ->
                when (effect) {
                    is SettingEffect.ApplyTheme -> {
                        AppThemeManager.applyTheme(effect.theme)
                    }

                    is SettingEffect.ShowThemeDialog -> {
                        showThemeDialog(effect.currentTheme) { selectedTheme ->
                            _settingViewModel.setEvent(SettingIntent.SetAppTheme(selectedTheme))
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            _shareSettingViewModel.uiState.collectWithLifecycle(lifecycle) { state ->
                with(binding) {
                    switchChangeTickerColor.isChecked = state.tickerChangeColor
                }
            }
        }
    }

    companion object {
        fun newInstance() = SettingFragment()
    }
}