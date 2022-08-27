package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.ticker.FavoriteTickerDao
import com.example.data.model.ticker.FavoriteTickerEntity

@Database(entities = [FavoriteTickerEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteTickerDao(): FavoriteTickerDao

    companion object {
        const val DB_FILE_NAME = "app.db"
    }
}