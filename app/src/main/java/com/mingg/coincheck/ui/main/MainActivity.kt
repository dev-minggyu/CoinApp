package com.mingg.coincheck.ui.main

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mingg.coincheck.R
import com.mingg.coincheck.databinding.ActivityMainBinding
import com.mingg.coincheck.extension.isServiceRunning
import com.mingg.coincheck.ui.base.BaseActivity
import com.mingg.coincheck.ui.floating.FloatingWindowService
import com.mingg.domain.usecase.ticker.SubscribeTickerUseCase
import com.mingg.domain.usecase.ticker.UnsubscribeTickerUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    LifecycleEventObserver {

    @Inject
    lateinit var subscribeTickerUseCase: SubscribeTickerUseCase

    @Inject
    lateinit var unsubscribeTickerUseCase: UnsubscribeTickerUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding.apply {
            bottomNavigationView.setupWithNavController(navHostFragment.navController)
            bottomNavigationView.setOnItemReselectedListener { }
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> subscribeTicker()
            Lifecycle.Event.ON_STOP -> unsubscribeTicker()
            else -> {}
        }
    }

    private fun subscribeTicker() {
        lifecycleScope.launch {
            subscribeTickerUseCase.execute()
        }
    }

    private fun unsubscribeTicker() {
        if (isServiceRunning(FloatingWindowService::class.java)) {
            lifecycleScope.launch {
                unsubscribeTickerUseCase.execute()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
    }
}