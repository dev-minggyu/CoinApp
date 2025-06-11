package com.ming.coincheck.ui.setting

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ming.coincheck.R
import com.ming.coincheck.databinding.FragmentSettingBinding
import com.ming.coincheck.extension.collectWithLifecycle
import com.ming.coincheck.ui.base.BaseFragment
import com.ming.coincheck.ui.main.SharedSettingIntent
import com.ming.coincheck.ui.main.SharedSettingViewModel
import com.ming.coincheck.utils.AppThemeManager
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

    private fun showThemeDialog(
        selectedTheme: String,
        callback: (selectedTheme: String) -> Unit
    ) {
        val items = resources.getStringArray(R.array.app_theme)
        val values = resources.getStringArray(R.array.app_theme_value)
        AlertDialog.Builder(requireContext()).setSingleChoiceItems(
            items,
            values.indexOf(selectedTheme)
        ) { dialog, which ->
            callback(
                when (items[which]) {
                    getString(R.string.app_theme_system) -> AppThemeManager.THEME_SYSTEM
                    getString(R.string.app_theme_light) -> AppThemeManager.THEME_LIGHT
                    getString(R.string.app_theme_dark) -> AppThemeManager.THEME_DARK
                    else -> throw IllegalArgumentException("Unknown item : ${items[which]}")
                }
            )
            dialog.dismiss()
        }.show()
    }
}