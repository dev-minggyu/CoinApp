package com.example.repository.di

import com.example.common.AtomicTickerListImpl
import com.example.database.favoriteticker.FavoriteTickerLocalDataSource
import com.example.database.favoriteticker.FavoriteTickerLocalDataSourceImpl
import com.example.database.myasset.MyAssetLocalDataSource
import com.example.database.myasset.MyAssetLocalDataSourceImpl
import com.example.database.setting.SettingLocalDataSource
import com.example.database.setting.SettingLocalDataSourceImpl
import com.example.domain.model.ticker.AtomicTickerList
import com.example.domain.repository.favoriteticker.FavoriteTickerRepository
import com.example.domain.repository.myasset.MyAssetRepository
import com.example.domain.repository.setting.SettingRepository
import com.example.domain.repository.ticker.TickerRepository
import com.example.network.ticker.TickerSocketService
import com.example.network.ticker.TickerSocketServiceImpl
import com.example.network.ticker.TickerSymbolRemoteDataSource
import com.example.network.ticker.TickerSymbolRemoteDataSourceImpl
import com.example.repository.FavoriteTickerRepositoryImpl
import com.example.repository.MyAssetRepositoryImpl
import com.example.repository.SettingRepositoryImpl
import com.example.repository.TickerRepositoryImpl
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
    abstract fun bindTickerSymbolRemoteDataSource(tickerSymbolRemoteDataSourceImpl: TickerSymbolRemoteDataSourceImpl): TickerSymbolRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindTickerSocketService(tickerSocketServiceImpl: TickerSocketServiceImpl): TickerSocketService

    @Binds
    @Singleton
    abstract fun bindTickerRepository(tickerRepositoryImpl: TickerRepositoryImpl): TickerRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteTickerLocalDataSource(favoriteTickerLocalDataSourceImpl: FavoriteTickerLocalDataSourceImpl): FavoriteTickerLocalDataSource

    @Binds
    @Singleton
    abstract fun bindFavoriteTickerRepository(favoriteTickerRepositoryImpl: FavoriteTickerRepositoryImpl): FavoriteTickerRepository

    @Binds
    @Singleton
    abstract fun bindAtomicTickerList(atomicTickerListImpl: AtomicTickerListImpl): AtomicTickerList

    @Binds
    @Singleton
    abstract fun bindSettingLocalDataSource(settingLocalDataSourceImpl: SettingLocalDataSourceImpl): SettingLocalDataSource

    @Binds
    @Singleton
    abstract fun bindSettingRepository(settingRepositoryImpl: SettingRepositoryImpl): SettingRepository

    @Binds
    @Singleton
    abstract fun bindMyAssetLocalDataSource(myAssetLocalDataSourceImpl: MyAssetLocalDataSourceImpl): MyAssetLocalDataSource

    @Binds
    @Singleton
    abstract fun bindMyAssetRepository(myAssetRepositoryImpl: MyAssetRepositoryImpl): MyAssetRepository
}