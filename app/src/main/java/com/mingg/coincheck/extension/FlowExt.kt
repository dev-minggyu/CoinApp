package com.mingg.coincheck.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

suspend fun <T> StateFlow<T>.collectWithLifecycle(
    lifecycle: Lifecycle,
    collector: FlowCollector<T>
) =
    flowWithLifecycle(lifecycle).collect(collector)