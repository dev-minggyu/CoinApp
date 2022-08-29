package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.favoriteticker.FavoriteTickerDao
import com.example.data.db.tickersymbol.TickerSymbolDao
import com.example.data.model.favoriteticker.FavoriteTickerEntity
import com.example.data.model.tickersymbol.TickerSymbolEntity

@Database(entities = [TickerSymbolEntity::class, FavoriteTickerEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tickerSymbolDao(): TickerSymbolDao

    abstract fun favoriteTickerDao(): FavoriteTickerDao

    companion object {
        const val DB_FILE_NAME = "app.db"
    }
}