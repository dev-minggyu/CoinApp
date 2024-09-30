package com.mingg.coincheck.ui.floating

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
import com.mingg.coincheck.databinding.FloatingViewBinding
import com.mingg.coincheck.ui.floating.adapter.FloatingListAdapter
import com.mingg.coincheck.utils.NotificationUtil
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
    lateinit var unfilteredTickerDataUseCase: UnfilteredTickerDataUseCase

    @Inject
    lateinit var settingFloatingTickerListUseCase: SettingFloatingTickerListUseCase

    @Inject
    lateinit var settingFloatingTransparentUseCase: SettingFloatingTransparentUseCase

    private val _serviceJob: Job = Job()
    private val _serviceScope = CoroutineScope(_serviceJob + Dispatchers.Default)

    private var windowView: FloatingViewBinding? = null
    private var viewPosX = 0f
    private var viewPosY = 0f

    private var binder = FloatingWindowServiceBinder(this)

    private var floatingList = listOf<String>()

    private var floatingListAdapter: FloatingListAdapter? = null

    override fun onBind(intent: Intent): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(this.hashCode(), NotificationUtil.notification(applicationContext))
        createView()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        windowView?.let {
            _serviceJob.cancel()
            floatingListAdapter = null
            val windowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.removeView(it.root)
            windowView = null
        }
    }

    @SuppressLint("ClickableViewAccessibility", "InlinedApi")
    private fun createView() {
        if (windowView != null) return

        val layoutInflater = applicationContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val windowManager = applicationContext.getSystemService(WINDOW_SERVICE) as WindowManager

        windowView = FloatingViewBinding.inflate(layoutInflater, null, false).also { binding ->
            val layoutParams = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
            )
            layoutParams.gravity = Gravity.CENTER
            windowManager.addView(binding.root, layoutParams)

            binding.layoutOverlay.setOnTouchListener { _, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        viewPosX = layoutParams.x - motionEvent.rawX
                        viewPosY = layoutParams.y - motionEvent.rawY
                        return@setOnTouchListener true
                    }

                    MotionEvent.ACTION_MOVE -> {
                        layoutParams.x = (viewPosX + motionEvent.rawX).toInt()
                        layoutParams.y = (viewPosY + motionEvent.rawY).toInt()
                        windowManager.updateViewLayout(binding.root, layoutParams)
                        return@setOnTouchListener true
                    }
                }
                return@setOnTouchListener false
            }

            floatingListAdapter = FloatingListAdapter()
            binding.rvTicker.apply {
                itemAnimator = null
                adapter = floatingListAdapter
            }
        }

        setTransparent(settingFloatingTransparentUseCase.get())
        observeTickerData()
    }

    fun setTransparent(value: Int) {
        windowView?.let {
            it.root.background.alpha = value
        }
    }

    fun setFloatingList(list: List<String>) {
        floatingList = list
    }

    private fun observeTickerData() {
        floatingList = settingFloatingTickerListUseCase.get()
        _serviceScope.launch(Dispatchers.Main) {
            unfilteredTickerDataUseCase.execute().collect {
                when (it) {
                    is TickerResource.Update -> {
                        floatingListAdapter?.submitList(
                            it.data.tickerList.filter { ticker ->
                                floatingList.contains(ticker.symbol + ticker.currencyType.name)
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