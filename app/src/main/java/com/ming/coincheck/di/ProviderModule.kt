package com.ming.coincheck.di

import com.ming.network.mapper.ticker.TickerResponseMapperImpl
import com.ming.network.mapper.ticker.TickerResponseMapper
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
    abstract fun bindTickerMapperProvider(tickerResponseMapperImpl: TickerResponseMapperImpl): TickerResponseMapper
}