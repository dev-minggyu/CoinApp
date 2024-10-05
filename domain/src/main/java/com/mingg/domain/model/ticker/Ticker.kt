package com.mingg.domain.model.ticker

import com.mingg.domain.model.Diffable

data class Ticker(
    var symbol: String,
    val koreanSymbol: String = "",
    val englishSymbol: String = "",
    var currencyType: Currency,
    var currentPrice: String,
    var decimalCurrentPrice: String,
    var changePricePrevDay: String,
    var rate: String,
    var volume: String,
    var formattedVolume: String,
    var isFavorite: Boolean = false
) : Diffable<Ticker> {
    override fun areItemsTheSame(other: Ticker): Boolean {
        return this.symbol == other.symbol
    }

    override fun areContentsTheSame(other: Ticker): Boolean {
        return this == other
    }
}