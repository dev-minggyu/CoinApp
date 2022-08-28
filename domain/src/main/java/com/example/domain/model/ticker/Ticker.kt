package com.example.domain.model.ticker

data class Ticker(
    var symbol: String,
    var currencyType: String,
    var currentPrice: Double,
    var prevPrice: Double,
    var rate: Double,
    var volume: Double,
    var isFavorite: Boolean = false,
    var favoriteIndex: Long = -1
)