package com.example.data.mapper.favoriteticker

import com.example.data.model.favoriteticker.FavoriteTickerEntity

object FavoriteTickerMapper {
    fun mapperToFavoriteTickerEntity(symbol: String): FavoriteTickerEntity =
        FavoriteTickerEntity(
            symbol = symbol
        )
}