package com.mingg.coincheck.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mingg.coincheck.R
import com.mingg.coincheck.databinding.ActivityMainBinding
import com.mingg.coincheck.extension.collectWithLifecycle
import com.mingg.coincheck.ui.base.BaseActivity
import com.mingg.coincheck.ui.home.HomeFragment
import com.mingg.coincheck.ui.myasset.MyAssetFragment
import com.mingg.coincheck.ui.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val _mainViewModel: MainViewModel by viewModels()

    private val _shareSettingViewModel: ShareSettingViewModel by viewModels()

    companion object {
        private const val TAG_FRAGMENT_HOME = "TAG_FRAGMENT_HOME"
        private const val TAG_FRAGMENT_MY_ASSET = "TAG_FRAGMENT_MY_ASSET"
        private const val TAG_FRAGMENT_SETTING = "TAG_FRAGMENT_SETTING"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = _mainViewModel

        setupObserver()

        _shareSettingViewModel.loadSettings()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            _mainViewModel.currentMenuId.collectWithLifecycle(lifecycle) {
                showFragment(it)
            }
        }
    }

    private fun showFragment(fragmentId: Int) {
        when (fragmentId) {
            R.id.home_fragment -> showFragment(TAG_FRAGMENT_HOME, HomeFragment.newInstance())
            R.id.my_asset_fragment -> showFragment(
                TAG_FRAGMENT_MY_ASSET,
                MyAssetFragment.newInstance()
            )

            R.id.setting_fragment -> showFragment(
                TAG_FRAGMENT_SETTING,
                SettingFragment.newInstance()
            )
        }
    }

    private fun showFragment(tag: String, fragment: Fragment) {
        supportFragmentManager.run {
            val transaction = beginTransaction()
            if (findFragmentByTag(tag) == null) {
                transaction.add(binding.navHostFragment.id, fragment, tag)
            }
            fragments.forEach {
                if (it.tag != tag) {
                    transaction.hide(it)
                } else {
                    transaction.show(it)
                }
            }
            transaction.commit()
        }
    }
}