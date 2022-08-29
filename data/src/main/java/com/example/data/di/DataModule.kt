package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.BuildConfig
import com.example.data.api.ApiService
import com.example.data.db.AppDatabase
import com.example.data.db.favoriteticker.FavoriteTickerDao
import com.example.data.db.tickersymbol.TickerSymbolDao
import com.example.data.model.ticker.AtomicTickerList
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.websocket.*
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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
    fun provideTickerSymbolDao(appDB: AppDatabase): TickerSymbolDao {
        return appDB.tickerSymbolDao()
    }

    @Singleton
    @Provides
    fun provideFavoriteTickerDao(appDB: AppDatabase): FavoriteTickerDao {
        return appDB.favoriteTickerDao()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(loggingInterceptor)
        }

        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(WebSockets)
        }
    }

    @Provides
    @Singleton
    fun provideAtomicTickerList(): AtomicTickerList {
        return AtomicTickerList()
    }
}