package com.example.database.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.example.database.AppDatabase
import com.example.database.data.favoriteticker.FavoriteTickerDao
import com.example.database.data.myasset.MyAssetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, AppDatabase.DB_FILE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavoriteTickerDao(appDB: AppDatabase): FavoriteTickerDao {
        return appDB.favoriteTickerDao()
    }

    @Singleton
    @Provides
    fun provideMyAssetDao(appDB: AppDatabase): MyAssetDao {
        return appDB.myAssetDao()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
}