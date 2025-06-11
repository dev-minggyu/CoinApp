package com.ming.repository.di

import com.ming.common.AtomicTickerListImpl
import com.ming.database.favoriteticker.FavoriteTickerLocalDataSource
import com.ming.database.favoriteticker.FavoriteTickerLocalDataSourceImpl
import com.ming.database.myasset.MyAssetLocalDataSource
import com.ming.database.myasset.MyAssetLocalDataSourceImpl
import com.ming.database.setting.SettingLocalDataSource
import com.ming.database.setting.SettingLocalDataSourceImpl
import com.ming.domain.model.ticker.AtomicTickerList
import com.ming.domain.repository.favoriteticker.FavoriteTickerRepository
import com.ming.domain.repository.myasset.MyAssetRepository
import com.ming.domain.repository.setting.SettingRepository
import com.ming.domain.repository.ticker.TickerRepository
import com.ming.network.ticker.TickerSocketService
import com.ming.network.ticker.TickerSocketServiceImpl
import com.ming.network.ticker.TickerSymbolRemoteDataSource
import com.ming.network.ticker.TickerSymbolRemoteDataSourceImpl
import com.ming.repository.FavoriteTickerRepositoryImpl
import com.ming.repository.MyAssetRepositoryImpl
import com.ming.repository.SettingRepositoryImpl
import com.ming.repository.TickerRepositoryImpl
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