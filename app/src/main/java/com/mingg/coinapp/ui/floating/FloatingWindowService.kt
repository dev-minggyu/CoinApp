package com.mingg.coinapp.ui.floating

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
import com.mingg.coinapp.databinding.FloatingViewBinding
import com.mingg.coinapp.ui.floating.adapter.FloatingListAdapter
import com.mingg.coinapp.utils.NotificationUtil
import com.mingg.domain.usecase.setting.SettingFloatingTickerListUseCase
import com.mingg.domain.usecase.setting.SettingFloatingTransparentUseCase
import com.mingg.domain.usecase.ticker.UnfilteredTickerDataUseCase
import com.mingg.domain.utils.TickerResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FloatingWindowService : Service() {
    @Inject
    lateinit var _unfilteredTickerDataUseCase: UnfilteredTickerDataUseCase

    @Inject
    lateinit var _settingFloatingTickerListUseCase: SettingFloatingTickerListUseCase

    @Inject
    lateinit var _settingFloatingTransparentUseCase: SettingFloatingTransparentUseCase

    private val _serviceJob: Job = Job()
    private val _serviceScope = CoroutineScope(_serviceJob + Dispatchers.Default)

    private var _windowView: FloatingViewBinding? = null
    private var _viewPosX = 0f
    private var _viewPosY = 0f

    private var _binder = FloatingWindowServiceBinder(this)

    private var _floatingList = listOf<String>()

    private var _floatingListAdapter: FloatingListAdapter? = null

    override fun onBind(intent: Intent): IBinder = _binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(this.hashCode(), NotificationUtil.notification(applicationContext))
        }
        createView()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        _windowView?.let {
            _serviceJob.cancel()
            _floatingListAdapter = null
            val windowManager =
                applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.removeView(it.root)
            _windowView = null
        }
    }

    @SuppressLint("ClickableViewAccessibility", "InlinedApi")
    private fun createView() {
        if (_windowView != null) return

        val layoutInflater =
            applicationContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val windowManager = applicationContext.getSystemService(WINDOW_SERVICE) as WindowManager

        _windowView = FloatingViewBinding.inflate(layoutInflater, null, false).also { binding ->
            val layoutParams = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY.takeIf { Build.VERSION.SDK_INT >= Build.VERSION_CODES.O }
                    ?: WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
            )
            layoutParams.gravity = Gravity.CENTER
            windowManager.addView(binding.root, layoutParams)

            binding.layoutOverlay.setOnTouchListener { _, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        _viewPosX = layoutParams.x - motionEvent.rawX
                        _viewPosY = layoutParams.y - motionEvent.rawY
                        return@setOnTouchListener true
                    }

                    MotionEvent.ACTION_MOVE -> {
                        layoutParams.x = (_viewPosX + motionEvent.rawX).toInt()
                        layoutParams.y = (_viewPosY + motionEvent.rawY).toInt()
                        windowManager.updateViewLayout(binding.root, layoutParams)
                        return@setOnTouchListener true
                    }
                }
                return@setOnTouchListener false
            }

            _floatingListAdapter = FloatingListAdapter()
            binding.rvTicker.apply {
                itemAnimator = null
                adapter = _floatingListAdapter
            }
        }

        setTransparent(_settingFloatingTransparentUseCase.get())
        observeTickerData()
    }

    fun setTransparent(value: Int) {
        _windowView?.let {
            it.root.background.alpha = value
        }
    }

    fun setFloatingList(list: List<String>) {
        _floatingList = list
    }

    private fun observeTickerData() {
        _floatingList = _settingFloatingTickerListUseCase.get()
        _serviceScope.launch(Dispatchers.Main) {
            _unfilteredTickerDataUseCase.execute().collect {
                when (it) {
                    is TickerResource.Update -> {
                        _floatingListAdapter?.submitList(
                            it.data.tickerList.filter { ticker ->
                                _floatingList.contains(ticker.symbol + ticker.currencyType.name)
                            }
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    companion object {
        fun startService(context: Context, serviceConnection: ServiceConnection? = null) {
            val intent = Intent(context, FloatingWindowService::class.java)
            context.startService(intent)
            serviceConnection?.let {
                context.bindService(intent, it, Context.BIND_AUTO_CREATE)
            }
        }

        fun stopService(context: Context, serviceConnection: ServiceConnection? = null) {
            serviceConnection?.let {
                context.unbindService(it)
            }
            context.stopService(
                Intent(context, FloatingWindowService::class.java)
            )
        }

        fun unbindService(context: Context, serviceConnection: ServiceConnection) {
            context.unbindService(serviceConnection)
        }
    }
}