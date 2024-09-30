package com.mingg.coincheck.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.mingg.coincheck.databinding.ActivityInitBinding
import com.mingg.coincheck.extension.collectWithLifecycle
import com.mingg.coincheck.ui.base.BaseActivity
import com.mingg.coincheck.ui.floating.FloatingWindowService
import com.mingg.coincheck.ui.main.MainActivity
import com.mingg.coincheck.utils.ActivityOverlayPermissionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InitActivity : BaseActivity<ActivityInitBinding>(ActivityInitBinding::inflate) {
    private val _initViewModel: InitViewModel by viewModels()

    private lateinit var _overlayPermissionManager: ActivityOverlayPermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        _overlayPermissionManager = ActivityOverlayPermissionManager.from(this)

        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            _initViewModel.isEnableFloatingWindow.collectWithLifecycle(lifecycle) {
                if (it) {
                    initOverlayService()
                } else {
                    gotoMain()
                }
            }
        }
    }

    private fun gotoMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun initOverlayService() {
        _overlayPermissionManager.checkPermission {
            if (it) {
                FloatingWindowService.startService(applicationContext)
            } else {
                _initViewModel.disableFloatingWindow()
            }
            gotoMain()
        }
    }
}