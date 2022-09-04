package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.favoriteticker.FavoriteTickerDao
import com.example.data.db.myasset.MyAssetDao
import com.example.data.model.favoriteticker.FavoriteTickerEntity
import com.example.data.model.myasset.MyTickerEntity

@Database(entities = [FavoriteTickerEntity::class, MyTickerEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteTickerDao(): FavoriteTickerDao

    abstract fun myAssetDao(): MyAssetDao

    companion object {
        const val DB_FILE_NAME = "app.db"
    }
}