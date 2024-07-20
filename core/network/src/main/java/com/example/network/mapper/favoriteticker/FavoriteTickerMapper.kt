package com.example.network.mapper.favoriteticker

import com.example.domain.model.favoriteticker.FavoriteTicker
import com.example.database.data.favoriteticker.FavoriteTickerEntity

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