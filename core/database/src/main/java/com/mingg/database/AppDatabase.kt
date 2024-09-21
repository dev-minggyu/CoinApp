package com.mingg.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mingg.database.data.favoriteticker.FavoriteTickerDao
import com.mingg.database.data.myasset.MyAssetDao
import com.mingg.database.data.favoriteticker.FavoriteTickerEntity
import com.mingg.database.data.myasset.MyTickerEntity

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