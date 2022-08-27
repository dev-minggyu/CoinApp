package com.example.data.db.ticker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.favorite.FavoriteTickerEntity

@Dao
interface FavoriteTickerDao {
    @Query("SELECT * FROM favorite")
    suspend fun getFavoriteTickers(): List<FavoriteTickerEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteTicker(symbol: FavoriteTickerEntity): Long

    /**
     * @return the number of favorite deleted. This should always be 1.
     */
    @Query("DELETE FROM favorite WHERE symbol = :symbol")
    suspend fun deleteFavoriteSymbol(symbol: String): Int

    @Query("DELETE FROM favorite")
    suspend fun deleteAllFavorite(): Int
}