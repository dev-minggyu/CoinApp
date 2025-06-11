package com.ming.database.data.favoriteticker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteTickerDao {
    @Query("SELECT * FROM ticker_favorite")
    suspend fun getFavoriteTickerList(): List<FavoriteTickerEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteTicker(symbol: FavoriteTickerEntity): Long

    /**
     * @return the number of favorite deleted. This should always be 1.
     */
    @Query("DELETE FROM ticker_favorite WHERE symbol = :symbol")
    suspend fun deleteFavoriteSymbol(symbol: String): Int

    @Query("DELETE FROM ticker_favorite")
    suspend fun deleteAllFavorite(): Int
}