package com.example.data.mapper

import com.example.data.model.favoriteticker.FavoriteTickerEntity
import com.example.domain.model.ticker.Currency
import com.example.domain.model.ticker.Ticker

object FavoriteTickerMapper {
    fun mapperToFavoriteTickerEntity(symbol: String): FavoriteTickerEntity =
        FavoriteTickerEntity(
            symbol = symbol
        )

    fun mapperToTicker(favoriteTickerEntity: FavoriteTickerEntity): Ticker {
        val splitSymbol = favoriteTickerEntity.symbol.split("-")
        return Ticker(
            symbol = splitSymbol[1],
            currencyType = Currency.valueOf(splitSymbol[0]),
            currentPrice = "",
            decimalCurrentPrice = "",
            prevPrice = "",
            rate = "",
            volume = "",
            dividedVolume = "",
            isFavorite = true,
            favoriteIndex = favoriteTickerEntity.id
        )
    }
}