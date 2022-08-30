package com.example.data.mapper.favoriteticker

import com.example.data.model.favoriteticker.FavoriteTickerEntity
import com.example.domain.model.favoriteticker.FavoriteTicker

object FavoriteTickerMapper {
    fun mapperToFavoriteTickerEntity(symbol: String): FavoriteTickerEntity =
        FavoriteTickerEntity(
            symbol = symbol
        )

    fun mapperToFavoriteTicker(favoriteTickerEntity: FavoriteTickerEntity): FavoriteTicker =
        FavoriteTicker(
            favoriteTickerEntity.symbol
        )
}