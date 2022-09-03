package com.example.coinapp.di

import com.example.coinapp.provider.TickerMapperProviderImpl
import com.example.data.mapper.ticker.TickerMapperProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProviderModule {
    @Binds
    @Singleton
    abstract fun bindTickerMapperProvider(tickerMapperProviderImpl: TickerMapperProviderImpl): TickerMapperProvider
}