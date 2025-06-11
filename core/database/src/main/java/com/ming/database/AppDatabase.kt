package com.ming.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ming.database.data.favoriteticker.FavoriteTickerDao
import com.ming.database.data.myasset.MyAssetDao
import com.ming.database.data.favoriteticker.FavoriteTickerEntity
import com.ming.database.data.myasset.MyTickerEntity

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