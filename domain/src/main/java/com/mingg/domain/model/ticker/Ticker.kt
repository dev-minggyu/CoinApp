package com.mingg.domain.model.ticker

import com.mingg.domain.model.Diffable

data class Ticker(
    val symbol: String,
    val koreanSymbol: String = "",
    val englishSymbol: String = "",
    val currencyType: Currency,
    val currentPrice: String,
    val decimalCurrentPrice: String,
    val changePricePrevDay: String,
    val rate: String,
    val volume: String,
    val formattedVolume: String,
    val isFavorite: Boolean = false,
    val isVolumeDividedByMillion: Boolean = false
) : Diffable<Ticker> {
    override fun areItemsTheSame(other: Ticker): Boolean {
        return this.symbol == other.symbol
    }

    override fun areContentsTheSame(other: Ticker): Boolean {
        return this == other
    }
}