package com.example.coinapp.ui.setting.floatingwindow

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.coinapp.R
import com.example.coinapp.ui.base.BaseActivity
import com.example.coinapp.databinding.ActivityFloatingWindowSettingBinding
import com.example.coinapp.ui.floating.FloatingWindowService
import com.example.coinapp.ui.floating.FloatingWindowServiceBinder
import com.example.coinapp.utils.ActivityOverlayPermissionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FloatingWindowSettingActivity : BaseActivity<ActivityFloatingWindowSettingBinding>(R.layout.activity_floating_window_setting) {
    private val _floatingWindowSettingViewModel: FloatingWindowSettingViewModel by viewModels()

    private var _floatingWindowService: FloatingWindowService? = null

    private val _serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName?, binder: IBinder?) {
            _floatingWindowService = (binder as FloatingWindowServiceBinder).getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {}
    }

    private lateinit var _overlayPermissionManager: ActivityOverlayPermissionManager

    private val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        when (result.resultCode) {
            REQUEST_CHECKED_FLOATING_SYMBOL -> {
                result.data?.getStringArrayListExtra(FloatingTickerSelectActivity.KEY_CHECKED_FLOATING_SYMBOL_LIST)?.let {
                    _floatingWindowService?.setFloatingList(it.toList())
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            _overlayPermissionManager = ActivityOverlayPermissionManager.from(this)
        }

        loadSettings()
    }

    private fun loadSettings() {
        _floatingWindowSettingViewModel.isEnableFloatingWindow().also {
            binding.switchEnableFloatingWindow.isChecked = it
            if (it) startFloatingWindowService()
        }

        binding.seekbarFloatingWindowTransparent.progress = _floatingWindowSettingViewModel.getFloatingWindowTransparent()
    }

    fun settingMenuClick(id: Int) {
        when (id) {
            R.id.tv_select_floating_ticker -> {
                FloatingTickerSelectActivity.createIntent(this).also {
                    activityResult.launch(it)
                }
            }
        }
    }

    fun settingSwitchClick(id: Int, isChecked: Boolean) {
        when (id) {
            R.id.switch_enable_floating_window -> {
                _floatingWindowSettingViewModel.setEnableFloatingWindow(isChecked)
                if (isChecked) {
                    startFloatingWindowService()
                } else {
                    FloatingWindowService.stopService(this, _serviceConnection)
                    _floatingWindowService = null
                }
            }
        }
    }

    fun settingProgressChanged(id: Int, value: Int) {
        when (id) {
            R.id.seekbar_floating_window_transparent -> {
                _floatingWindowSettingViewModel.setFloatingWindowTransparent(value)
                _floatingWindowService?.setTransparent(value)
            }
        }
    }

    private fun startFloatingWindowService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            _overlayPermissionManager.checkPermission {
                if (it) {
                    FloatingWindowService.startService(this, _serviceConnection)
                }
            }
        } else {
            FloatingWindowService.startService(this, _serviceConnection)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _floatingWindowService?.let {
            FloatingWindowService.unbindService(this, _serviceConnection)
            _floatingWindowService = null
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