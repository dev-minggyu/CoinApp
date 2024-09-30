package com.mingg.coincheck.di

import com.mingg.coincheck.provider.TickerMapperProviderImpl
import com.mingg.network.mapper.ticker.TickerMapperProvider
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