package com.ming.coincheck.ui.floating

import android.os.Binder
import java.lang.ref.WeakReference

class FloatingWindowServiceBinder(floatingWindowService: FloatingWindowService) :
    Binder() {
    private val overlayService = WeakReference(floatingWindowService)

    fun getService() = overlayService.get()
}