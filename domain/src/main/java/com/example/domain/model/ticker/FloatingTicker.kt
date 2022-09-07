package com.example.domain.model.ticker

data class FloatingTicker(
    val symbol: String,
    val koreanSymbol: String,
    val englishSymbol: String,
    var isChecked: Boolean
)
