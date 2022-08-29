package com.example.coinapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.coinapp.R
import com.example.coinapp.base.BaseActivity
import com.example.coinapp.databinding.ActivityInitBinding
import com.example.coinapp.extension.collectWithLifecycle
import com.example.coinapp.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InitActivity : BaseActivity<ActivityInitBinding>(R.layout.activity_init) {
    private val _initViewModel: InitViewModel by viewModels()

    private var _isReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        binding.vm = _initViewModel
        setupSplashScreen()
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            _initViewModel.isSuccessGetTickerSymbol.collectWithLifecycle(lifecycle) {
                _isReady = it
            }
        }
    }

    private fun gotoMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun setupSplashScreen() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                return if (_isReady) {
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    gotoMain()
                    true
                } else {
                    false
                }
            }
        })
    }
}