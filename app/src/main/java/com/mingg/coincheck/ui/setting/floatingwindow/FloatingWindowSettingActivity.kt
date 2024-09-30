package com.mingg.coincheck.ui.setting.floatingwindow

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.SeekBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mingg.coincheck.databinding.ActivityFloatingWindowSettingBinding
import com.mingg.coincheck.extension.collectWithLifecycle
import com.mingg.coincheck.ui.base.BaseActivity
import com.mingg.coincheck.ui.floating.FloatingWindowService
import com.mingg.coincheck.ui.floating.FloatingWindowServiceBinder
import com.mingg.coincheck.utils.ActivityOverlayPermissionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FloatingWindowSettingActivity :
    BaseActivity<ActivityFloatingWindowSettingBinding>(ActivityFloatingWindowSettingBinding::inflate) {

    private val floatingWindowSettingViewModel: FloatingWindowSettingViewModel by viewModels()

    private var floatingWindowService: FloatingWindowService? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName?, binder: IBinder?) {
            floatingWindowService = (binder as FloatingWindowServiceBinder).getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {}
    }

    private lateinit var overlayPermissionManager: ActivityOverlayPermissionManager

    private val activityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                REQUEST_CHECKED_FLOATING_SYMBOL -> {
                    result.data?.getStringArrayListExtra(FloatingTickerSelectActivity.KEY_CHECKED_FLOATING_SYMBOL_LIST)
                        ?.let {
                            floatingWindowService?.setFloatingList(it.toList())
                        }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overlayPermissionManager = ActivityOverlayPermissionManager.from(this)
        setupListener()
        setupObservers()
        floatingWindowSettingViewModel.setEvent(FloatingWindowSettingIntent.LoadSettings)
    }

    private fun setupListener() {
        binding.switchEnableFloatingWindow.setOnCheckedChangeListener { _, isChecked ->
            floatingWindowSettingViewModel.setEvent(
                FloatingWindowSettingIntent.SetEnableFloatingWindow(
                    isChecked
                )
            )
            if (isChecked) {
                startFloatingWindowService()
            } else {
                FloatingWindowService.stopService(this, serviceConnection)
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
            FloatingTickerSelectActivity.createIntent(this).also {
                activityResult.launch(it)
            }
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
                FloatingWindowService.startService(this, serviceConnection)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        floatingWindowService?.let {
            FloatingWindowService.unbindService(this, serviceConnection)
            floatingWindowService = null
        }
    }

    companion object {
        const val REQUEST_CHECKED_FLOATING_SYMBOL = 0

        fun startActivity(context: Context) {
            context.startActivity(
                Intent(context, FloatingWindowSettingActivity::class.java)
            )
        }
    }
}