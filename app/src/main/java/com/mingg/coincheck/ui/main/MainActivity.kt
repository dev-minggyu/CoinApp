package com.mingg.coincheck.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mingg.coincheck.R
import com.mingg.coincheck.databinding.ActivityMainBinding
import com.mingg.coincheck.navigation.NavigationManager
import com.mingg.coincheck.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navigationManager = NavigationManager(navHostFragment.navController)
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)
        binding.bottomNavigationView.setOnItemReselectedListener { }
    }
}