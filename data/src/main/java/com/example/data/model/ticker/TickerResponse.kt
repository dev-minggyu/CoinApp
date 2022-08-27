package com.example.data.model.ticker

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TickerResponse(
    @SerialName("english_name")
    val englishName: String,

    @SerialName("korean_name")
    val koreanName: String,

    @SerialName("market")
    val market: String
)