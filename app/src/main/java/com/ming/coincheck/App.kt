package com.ming.coincheck

import android.app.Application
import androidx.preference.PreferenceManager
import com.ming.coincheck.utils.AppThemeManager
import com.ming.coincheck.utils.NotificationUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        initTheme()
        NotificationUtil.createNotificationChannel(applicationContext)
    }

    private fun initTheme() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val theme = pref.getString(getString(R.string.key_pref_app_theme), AppThemeManager.THEME_SYSTEM)!!
        AppThemeManager.applyTheme(theme)
    }

    companion object {
        private lateinit var INSTANCE: App

        fun getString(resID: Int): String {
            return INSTANCE.getString(resID)
        }
    }
}