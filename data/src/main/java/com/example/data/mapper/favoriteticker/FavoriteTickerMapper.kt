package com.example.data.mapper.favoriteticker

import com.example.data.model.favoriteticker.FavoriteTickerEntity
import com.example.domain.model.ticker.Currency
import com.example.domain.model.ticker.Ticker

object FavoriteTickerMapper {
    fun mapperToFavoriteTickerEntity(symbol: String): FavoriteTickerEntity =
        FavoriteTickerEntity(
            symbol = symbol
        )
}