package com.example.data.mapper.favoriteticker

import com.example.data.model.favoriteticker.FavoriteTickerEntity
import com.example.domain.model.favoriteticker.FavoriteTicker

object FavoriteTickerMapper {
    fun toFavoriteTickerEntity(symbol: String): FavoriteTickerEntity =
        FavoriteTickerEntity(
            symbol = symbol
        )

    fun toFavoriteTicker(favoriteTickerEntity: FavoriteTickerEntity): FavoriteTicker =
        FavoriteTicker(
            favoriteTickerEntity.symbol
        )
}