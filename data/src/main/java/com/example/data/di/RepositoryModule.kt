package com.example.data.di

import com.example.data.repository.favoriteticker.FavoriteTickerRepositoryImpl
import com.example.data.repository.favoriteticker.local.FavoriteTickerLocalDataSource
import com.example.data.repository.favoriteticker.local.FavoriteTickerLocalDataSourceImpl
import com.example.data.repository.ticker.TickerRepositoryImpl
import com.example.data.repository.ticker.remote.TickerSocketService
import com.example.data.repository.ticker.remote.TickerSocketServiceImpl
import com.example.data.repository.tickersymbol.TickerSymbolRepositoryImpl
import com.example.data.repository.tickersymbol.local.TickerSymbolLocalDataSource
import com.example.data.repository.tickersymbol.local.TickerSymbolLocalDataSourceImpl
import com.example.data.repository.tickersymbol.remote.TickerSymbolRemoteDataSource
import com.example.data.repository.tickersymbol.remote.TickerSymbolRemoteDataSourceImpl
import com.example.domain.repository.favoriteticker.FavoriteTickerRepository
import com.example.domain.repository.ticker.TickerRepository
import com.example.domain.repository.tickersymbol.TickerSymbolRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTickerSymbolRepository(tickerSymbolRepositoryImpl: TickerSymbolRepositoryImpl): TickerSymbolRepository

    @Binds
    @Singleton
    abstract fun bindTickerSymbolRemoteDataSource(tickerSymbolRemoteDataSourceImpl: TickerSymbolRemoteDataSourceImpl): TickerSymbolRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindTickerSymbolLocalDataSource(tickerSymbolLocalDataSourceImpl: TickerSymbolLocalDataSourceImpl): TickerSymbolLocalDataSource

    @Binds
    @Singleton
    abstract fun bindTickerRepository(tickerRepositoryImpl: TickerRepositoryImpl): TickerRepository

    @Binds
    @Singleton
    abstract fun bindTickerSocketService(tickerSocketServiceImpl: TickerSocketServiceImpl): TickerSocketService

    @Binds
    @Singleton
    abstract fun bindFavoriteTickerLocalDataSource(favoriteTickerLocalDataSourceImpl: FavoriteTickerLocalDataSourceImpl): FavoriteTickerLocalDataSource

    @Binds
    @Singleton
    abstract fun bindFavoriteTickerRepository(favoriteTickerRepositoryImpl: FavoriteTickerRepositoryImpl): FavoriteTickerRepository
}