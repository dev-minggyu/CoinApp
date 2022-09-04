package com.example.data.repository.myasset.local;

import com.example.data.model.myasset.MyTickerEntity
import com.example.domain.model.myasset.MyTicker

interface MyAssetLocalDataSource {
    suspend fun getMyAssetList(): List<MyTickerEntity>

    suspend fun getMyAssetTicker(symbol: String, currency: String): MyTickerEntity?

    suspend fun insertMyTicker(tickerEntity: MyTickerEntity)

    suspend fun deleteMyTicker(symbol: String)

    suspend fun deleteAllMyTicker()
}
