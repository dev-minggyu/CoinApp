package com.ming.coincheck.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ming.coincheck.databinding.FragmentInitBinding
import com.ming.coincheck.extension.collectWithLifecycle
import com.ming.coincheck.ui.base.BaseFragment
import com.ming.coincheck.ui.floating.FloatingWindowService
import com.ming.coincheck.utils.ActivityOverlayPermissionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InitFragment : BaseFragment<FragmentInitBinding>(FragmentInitBinding::inflate) {

    private val initViewModel: InitViewModel by viewModels()

    private val overlayPermissionManager: ActivityOverlayPermissionManager = ActivityOverlayPermissionManager.from(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            initViewModel.uiState.collectWithLifecycle(lifecycle) { state ->
                if (state.isFloatingWindowEnabled) {
                    initOverlayService()
                } else {
                    navigationManager.navigateToHome()
                }
            }
        }
    }

    private fun initOverlayService() {
        overlayPermissionManager.checkPermission { isGranted ->
            if (isGranted) {
                FloatingWindowService.startService(requireContext())
            } else {
                initViewModel.setEvent(InitIntent.DisableFloatingWindow)
            }
            navigationManager.navigateToHome()
        }
    }
}