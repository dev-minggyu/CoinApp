package com.example.domain.model.ticker

data class Ticker(
    var symbol: String,
    var currencyType: Currency,
    var currentPrice: String,
    var decimalCurrentPrice: String,
    var prevPrice: String,
    var rate: String,
    var volume: String,
    var dividedVolume: String,
    var isFavorite: Boolean = false
)