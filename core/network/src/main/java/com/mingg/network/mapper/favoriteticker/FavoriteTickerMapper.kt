package com.mingg.network.mapper.favoriteticker

import com.mingg.database.data.favoriteticker.FavoriteTickerEntity
import com.mingg.domain.model.favoriteticker.FavoriteTicker

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