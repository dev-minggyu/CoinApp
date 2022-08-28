package com.example.data.model.ticker

import kotlinx.serialization.Serializable

@Serializable
data class TickerRequest(
    val codes: List<String>? = null,
    val type: String? = null,
    val ticket: String? = null
)