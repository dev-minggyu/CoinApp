package com.example.coinapp.extension

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.res.TypedArray
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

suspend fun <T> StateFlow<T>.collectWithLifecycle(lifecycle: Lifecycle, collector: FlowCollector<T>) =
    flowWithLifecycle(lifecycle).collect(collector)

inline fun <reified T : Enum<T>> TypedArray.getEnum(index: Int) =
    getInt(
        index,
        -1
    ).let { if (it >= 0) enumValues<T>()[it] else throw IllegalAccessException("No enum found matching index : $index") }

@Suppress("DEPRECATION")
fun <T> Context.isServiceRunning(service: Class<T>): Boolean {
    return (getSystemService(ACTIVITY_SERVICE) as ActivityManager)
        .getRunningServices(Integer.MAX_VALUE)
        .any { it.service.className == service.name }
}