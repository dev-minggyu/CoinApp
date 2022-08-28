package com.example.data.di

import com.example.data.repository.tickerlist.TickerListRepositoryImpl
import com.example.data.repository.tickerlist.local.TickerListLocalDataSource
import com.example.data.repository.tickerlist.local.TickerListLocalDataSourceImpl
import com.example.data.repository.tickerlist.remote.TickerListRemoteDataSource
import com.example.data.repository.tickerlist.remote.TickerListRemoteDataSourceImpl
import com.example.domain.repository.tickerlist.TickerListRepository
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
    abstract fun bindTickerListRepository(tickerListRepositoryImpl: TickerListRepositoryImpl): TickerListRepository

    @Binds
    @Singleton
    abstract fun bindTickerListRemoteDataSource(tickerListRemoteDataSourceImpl: TickerListRemoteDataSourceImpl): TickerListRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindTickerListLocalDataSource(tickerListLocalDataSourceImpl: TickerListLocalDataSourceImpl): TickerListLocalDataSource
}