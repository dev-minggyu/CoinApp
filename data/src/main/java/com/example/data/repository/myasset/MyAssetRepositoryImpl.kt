package com.example.data.repository.myasset

import com.example.data.mapper.myasset.MyTickerMapper
import com.example.data.repository.myasset.local.MyAssetLocalDataSource
import com.example.domain.model.myasset.MyTicker
import com.example.domain.repository.myasset.MyAssetRepository
import javax.inject.Inject

class MyAssetRepositoryImpl @Inject constructor(
    private val myAssetLocalDataSource: MyAssetLocalDataSource
) : MyAssetRepository {
    override suspend fun getMyAssetList(): List<MyTicker> =
        myAssetLocalDataSource.getMyAssetList().map {
            MyTickerMapper.toMyTicker(it)
        }

    override suspend fun insertMyTicker(ticker: MyTicker) {
        myAssetLocalDataSource.insertMyTicker(
            MyTickerMapper.toMyTickerEntity(ticker)
        )
    }

    override suspend fun deleteMyTicker(symbol: String) {
        myAssetLocalDataSource.deleteMyTicker(symbol)
    }

    override suspend fun deleteAllMyTicker() {
        myAssetLocalDataSource.deleteAllMyTicker()
    }
}