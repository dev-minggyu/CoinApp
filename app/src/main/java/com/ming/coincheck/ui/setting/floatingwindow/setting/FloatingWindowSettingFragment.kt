package com.ming.coincheck.ui.setting.floatingwindow.setting

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ming.coincheck.databinding.FragmentFloatingWindowSettingBinding
import com.ming.coincheck.extension.collectWithLifecycle
import com.ming.coincheck.ui.base.BaseFragment
import com.ming.coincheck.ui.floating.FloatingWindowService
import com.ming.coincheck.ui.floating.FloatingWindowServiceBinder
import com.ming.coincheck.ui.setting.floatingwindow.selector.FloatingTickerSelectFragment
import com.ming.coincheck.utils.ActivityOverlayPermissionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FloatingWindowSettingFragment :
    BaseFragment<FragmentFloatingWindowSettingBinding>(FragmentFloatingWindowSettingBinding::inflate) {

    private val floatingWindowSettingViewModel: FloatingWindowSettingViewModel by viewModels()

    private var floatingWindowService: FloatingWindowService? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName?, binder: IBinder?) {
            floatingWindowService = (binder as FloatingWindowServiceBinder).getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {}
    }

    private val overlayPermissionManager: ActivityOverlayPermissionManager =
        ActivityOverlayPermissionManager.from(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        setupObservers()
        floatingWindowSettingViewModel.setEvent(FloatingWindowSettingIntent.LoadSettings)
    }

    private fun setupListener() {
        setFragmentResultListener(REQUEST_CHECKED_FLOATING_TICKER) { _, bundle ->
            val resultList = bundle.getStringArrayList(FloatingTickerSelectFragment.KEY_CHECKED_FLOATING_TICKER_LIST)
            resultList?.let {
                floatingWindowService?.setFloatingList(it.toList())
            }
        }

        binding.switchEnableFloatingWindow.setOnCheckedChangeListener { _, isChecked ->
            floatingWindowSettingViewModel.setEvent(
                FloatingWindowSettingIntent.SetEnableFloatingWindow(
                    isChecked
                )
            )
            if (isChecked) {
                startFloatingWindowService()
            } else {
                FloatingWindowService.stopService(requireContext(), serviceConnection)
                floatingWindowService = null
            }
        }

        binding.seekbarFloatingWindowTransparent.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) = Unit

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    floatingWindowSettingViewModel.setEvent(FloatingWindowSettingIntent.SetFloatingWindowTransparency(it.progress))
                    floatingWindowService?.setTransparent(it.progress)
                }
            }
        })

        binding.tvSelectFloatingTicker.setOnClickListener {
            navigationManager.navigateToFloatingTickerSelect()
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            floatingWindowSettingViewModel.uiState.collectWithLifecycle(lifecycle) { state ->
                binding.switchEnableFloatingWindow.isChecked = state.isFloatingWindowEnabled
                binding.seekbarFloatingWindowTransparent.progress = state.floatingWindowTransparency

                if (state.isFloatingWindowEnabled) {
                    startFloatingWindowService()
                }
            }
        }
    }

    private fun startFloatingWindowService() {
        overlayPermissionManager.checkPermission {
            if (it) {
                FloatingWindowService.startService(requireContext(), serviceConnection)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        floatingWindowService?.let {
            FloatingWindowService.unbindService(requireContext(), serviceConnection)
            floatingWindowService = null
        }
    }

    companion object {
        const val REQUEST_CHECKED_FLOATING_TICKER = "REQUEST_CHECKED_FLOATING_TICKER"
    }
}