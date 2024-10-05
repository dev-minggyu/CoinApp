package com.mingg.coincheck.ui.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mingg.coincheck.databinding.FragmentSettingBinding
import com.mingg.coincheck.extension.collectWithLifecycle
import com.mingg.coincheck.extension.showThemeDialog
import com.mingg.coincheck.ui.base.BaseFragment
import com.mingg.coincheck.ui.main.SharedSettingIntent
import com.mingg.coincheck.ui.main.SharedSettingViewModel
import com.mingg.coincheck.utils.AppThemeManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    private val settingViewModel: SettingViewModel by viewModels()

    private val sharedSettingViewModel: SharedSettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        setupObservers()
    }

    private fun setupListener() {
        with(binding) {
            tvTheme.setOnClickListener {
                settingViewModel.setEvent(SettingIntent.GetAppTheme)
            }
            switchChangeTickerColor.setOnCheckedChangeListener { _, isChecked ->
                sharedSettingViewModel.setEvent(SharedSettingIntent.SetChangeTickerColor(isChecked))
            }
            tvFloatingWindowSetting.setOnClickListener {
                navigationManager.navigateToFloatingWindow()
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            settingViewModel.effect.collect { effect ->
                when (effect) {
                    is SettingEffect.ApplyTheme -> {
                        AppThemeManager.applyTheme(effect.theme)
                    }

                    is SettingEffect.ShowThemeDialog -> {
                        showThemeDialog(effect.currentTheme) { selectedTheme ->
                            settingViewModel.setEvent(SettingIntent.SetAppTheme(selectedTheme))
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            sharedSettingViewModel.uiState.collectWithLifecycle(lifecycle) { state ->
                with(binding) {
                    switchChangeTickerColor.isChecked = state.tickerChangeColor
                }
            }
        }
    }
}