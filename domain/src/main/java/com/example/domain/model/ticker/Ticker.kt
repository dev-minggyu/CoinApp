package com.example.domain.model.ticker

data class Ticker(
    var symbol: String,
    var currencyType: Currency,
    var currentPrice: String,
    var prevPrice: String,
    var rate: String,
    var volume: String,
    var isFavorite: Boolean = false,
    var favoriteIndex: Long = -1
)