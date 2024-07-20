package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.data.favoriteticker.FavoriteTickerDao
import com.example.database.data.myasset.MyAssetDao
import com.example.database.data.favoriteticker.FavoriteTickerEntity
import com.example.database.data.myasset.MyTickerEntity

@Database(
    entities = [FavoriteTickerEntity::class, MyTickerEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteTickerDao(): FavoriteTickerDao

    abstract fun myAssetDao(): MyAssetDao

    companion object {
        const val DB_FILE_NAME = "app.db"
    }
}