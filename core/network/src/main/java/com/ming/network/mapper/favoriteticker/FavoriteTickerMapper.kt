package com.ming.network.mapper.favoriteticker

import com.ming.database.data.favoriteticker.FavoriteTickerEntity
import com.ming.domain.model.favoriteticker.FavoriteTicker

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