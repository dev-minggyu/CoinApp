package com.mingg.coinapp.extension

import androidx.appcompat.app.AlertDialog
import com.mingg.coinapp.R
import com.mingg.coinapp.ui.setting.SettingFragment
import com.mingg.coinapp.utils.AppThemeManager

fun SettingFragment.showThemeDialog(
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
                getString(R.string.app_theme_system) -> {
                    AppThemeManager.THEME_SYSTEM
                }

                getString(R.string.app_theme_light) -> {
                    AppThemeManager.THEME_LIGHT
                }

                getString(R.string.app_theme_dark) -> {
                    AppThemeManager.THEME_DARK
                }

                else -> throw IllegalArgumentException("Unknown item : ${items[which]}")
            }
        )
        dialog.dismiss()
    }.show()
}