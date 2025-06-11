package com.ming.coincheck.navigation

import android.os.Bundle
import androidx.navigation.NavController
import com.ming.coincheck.R

class NavigationManager(val navController: NavController) {
    fun navigateToSplash() {
        navController.navigate(R.id.initFragment)
    }

    fun navigateToHome() {
        navController.navigate(R.id.homeFragment)
    }

    fun navigateToMyAsset() {
        navController.navigate(R.id.myAssetFragment)
    }

    fun navigateToSetting() {
        navController.navigate(R.id.settingFragment)
    }

    fun navigateToTickerDetail(bundle: Bundle) {
        navController.navigate(R.id.tickerDetailFragment, bundle)
    }

    fun navigateToFloatingWindow() {
        navController.navigate(R.id.floatingWindowSettingFragment)
    }

    fun navigateToFloatingTickerSelect() {
        navController.navigate(R.id.floatingTickerSelectFragment)
    }

    fun goBack() {
        navController.popBackStack()
    }
}