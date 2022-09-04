package com.example.data.repository.myasset.local;

import com.example.data.model.myasset.MyTickerEntity

interface MyAssetLocalDataSource {
    suspend fun getMyAssetList(): List<MyTickerEntity>

    suspend fun insertMyTicker(tickerEntity: MyTickerEntity)

    suspend fun deleteMyTicker(symbol: String)

    suspend fun deleteAllMyTicker()
}
