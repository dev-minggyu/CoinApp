package com.example.coinapp.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.coinapp.R
import com.example.coinapp.databinding.ActivityInitBinding
import com.example.coinapp.extension.collectWithLifecycle
import com.example.coinapp.ui.base.BaseActivity
import com.example.coinapp.ui.floating.FloatingWindowService
import com.example.coinapp.ui.main.MainActivity
import com.example.coinapp.utils.ActivityOverlayPermissionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InitActivity : BaseActivity<ActivityInitBinding>(R.layout.activity_init) {
    private val _initViewModel: InitViewModel by viewModels()

    private lateinit var _overlayPermissionManager: ActivityOverlayPermissionManager

    private var _isReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            _overlayPermissionManager = ActivityOverlayPermissionManager.from(this)
        }

        binding.vm = _initViewModel
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            _overlayPermissionManager
                .checkPermission {
                    if (it) {
                        FloatingWindowService.startService(applicationContext)
                    } else {
                        _initViewModel.disableFloatingWindow()
                    }
                    gotoMain()
                }
        } else {
            FloatingWindowService.startService(applicationContext)
        }
    }

    /**
     * Animation이 있는 Splash의 경우,
     * 해당 Animation이 다 그려졌는지 판단하는 코드.
     */
    private fun setupSplashScreen() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                return if (_isReady) {
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    true
                } else {
                    false
                }
            }
        })
    }
}