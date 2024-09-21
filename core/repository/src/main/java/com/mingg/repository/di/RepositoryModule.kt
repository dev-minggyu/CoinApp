package com.mingg.repository.di

import com.mingg.common.AtomicTickerListImpl
import com.mingg.database.favoriteticker.FavoriteTickerLocalDataSource
import com.mingg.database.favoriteticker.FavoriteTickerLocalDataSourceImpl
import com.mingg.database.myasset.MyAssetLocalDataSource
import com.mingg.database.myasset.MyAssetLocalDataSourceImpl
import com.mingg.database.setting.SettingLocalDataSource
import com.mingg.database.setting.SettingLocalDataSourceImpl
import com.mingg.domain.model.ticker.AtomicTickerList
import com.mingg.domain.repository.favoriteticker.FavoriteTickerRepository
import com.mingg.domain.repository.myasset.MyAssetRepository
import com.mingg.domain.repository.setting.SettingRepository
import com.mingg.domain.repository.ticker.TickerRepository
import com.mingg.network.ticker.TickerSocketService
import com.mingg.network.ticker.TickerSocketServiceImpl
import com.mingg.network.ticker.TickerSymbolRemoteDataSource
import com.mingg.network.ticker.TickerSymbolRemoteDataSourceImpl
import com.mingg.repository.FavoriteTickerRepositoryImpl
import com.mingg.repository.MyAssetRepositoryImpl
import com.mingg.repository.SettingRepositoryImpl
import com.mingg.repository.TickerRepositoryImpl
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