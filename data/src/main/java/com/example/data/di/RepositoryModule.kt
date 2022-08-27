package com.example.data.di

import com.example.data.repository.ticker.TickerRepositoryImpl
import com.example.data.repository.ticker.local.TickerLocalDataSource
import com.example.data.repository.ticker.remote.TickerRemoteDataSource
import com.example.data.repository.ticker.remote.TickerRemoteDataSourceImpl
import com.example.domain.repository.ticker.TickerRepository
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
    abstract fun bindExamRepository(examRepositoryImpl: TickerRepositoryImpl): TickerRepository

    @Binds
    @Singleton
    abstract fun bindExamRemoteDataSource(tickerRemoteDataSourceImpl: TickerRemoteDataSourceImpl): TickerRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindExamLocalDataSource(tickerLocalDataSource: TickerLocalDataSource): TickerLocalDataSource
}