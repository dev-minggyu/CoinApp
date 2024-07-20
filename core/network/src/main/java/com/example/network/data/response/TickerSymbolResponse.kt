package com.example.network.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TickerSymbolResponse(
    @SerialName("english_name")
    val englishName: String,

    @SerialName("korean_name")
    val koreanName: String,

    @SerialName("market")
    val market: String
)