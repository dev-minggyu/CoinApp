package com.example.coinapp

import android.app.Application
import androidx.preference.PreferenceManager
import com.example.coinapp.ui.floating.FloatingWindowService
import com.example.coinapp.utils.AppThemeManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        initTheme()
        initOverlayService()
    }

    private fun initTheme() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val theme = pref.getString(getString(R.string.key_pref_app_theme), AppThemeManager.THEME_SYSTEM)!!
        AppThemeManager.applyTheme(theme)
    }

    private fun initOverlayService() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val isEnabled = pref.getBoolean(getString(R.string.key_pref_enable_floating_window), false)
        if (isEnabled) {
            FloatingWindowService.startService(applicationContext)
        }
    }

    companion object {
        private lateinit var INSTANCE: App

        fun getString(resID: Int): String {
            return INSTANCE.getString(resID)
        }
    }
}