package com.example.coinapp.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.coinapp.R
import com.example.coinapp.base.BaseActivity
import com.example.coinapp.databinding.ActivitySplashBinding
import com.example.coinapp.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val _splashViewModel: SplashViewModel by viewModels()

    private var _isReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = _splashViewModel

        setupSplashScreen()

        setupObserver()

        _splashViewModel.getAllTickers()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            _splashViewModel.isSuccessGetAllTickers.flowWithLifecycle(lifecycle).collect {
                _isReady = it
                if (_isReady) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }
            }
        }
    }

    private fun setupSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val content: View = findViewById(android.R.id.content)
            content.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        return if (_isReady) {
                            content.viewTreeObserver.removeOnPreDrawListener(this)
                            true
                        } else {
                            false
                        }
                    }
                }
            )
        }
    }
}