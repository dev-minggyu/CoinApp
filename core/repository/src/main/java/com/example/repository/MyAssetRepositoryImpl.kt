package com.example.repository

import com.example.database.myasset.MyAssetLocalDataSource
import com.example.domain.model.myasset.MyTicker
import com.example.domain.repository.myasset.MyAssetRepository
import com.example.network.mapper.myasset.MyTickerMapper
import javax.inject.Inject

class MyAssetRepositoryImpl @Inject constructor(
    private val myAssetLocalDataSource: MyAssetLocalDataSource
) : MyAssetRepository {
    override suspend fun getMyAssetList(): List<MyTicker> =
        myAssetLocalDataSource.getMyAssetList().map {
            MyTickerMapper.toMyTicker(it)
        }

    override suspend fun getMyAssetTicker(
        symbol: String,
        currency: String
    ): MyTicker? =
        myAssetLocalDataSource.getMyAssetTicker(symbol, currency)?.let {
            MyTickerMapper.toMyTicker(it)
        }

    override suspend fun insertMyTicker(ticker: MyTicker) {
        myAssetLocalDataSource.insertMyTicker(
            MyTickerMapper.toMyTickerEntity(ticker)
        )
    }

    override suspend fun deleteMyTicker(symbol: String, currency: String) {
        myAssetLocalDataSource.deleteMyTicker(symbol, currency)
    }

    override suspend fun deleteAllMyTicker() {
        myAssetLocalDataSource.deleteAllMyTicker()
    }
}