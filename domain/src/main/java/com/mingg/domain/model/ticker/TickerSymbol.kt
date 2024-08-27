package com.mingg.domain.model.ticker

data class TickerSymbol(
    val symbol: String,
    val currency: Currency,
    val koreanSymbol: String,
    val englishSymbol: String,
)
