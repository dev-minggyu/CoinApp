package com.example.domain.model.ticker

data class Ticker(
    var symbol: String,
    var currencyType: Currency,
    var currentPrice: String,
    var decimalCurrentPrice: String,
    var changePricePrevDay: String,
    var decimalPrevPrice: String,
    var rate: String,
    var volume: String,
    var formattedVolume: String,
    var isFavorite: Boolean = false
)