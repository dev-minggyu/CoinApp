package com.ming.database.myasset;

import com.ming.database.data.myasset.MyTickerEntity

interface MyAssetLocalDataSource {
    suspend fun getMyAssetList(): List<MyTickerEntity>

    suspend fun getMyAssetTicker(symbol: String, currency: String): MyTickerEntity?

    suspend fun insertMyTicker(tickerEntity: MyTickerEntity)

    suspend fun deleteMyTicker(symbol: String, currency: String)

    suspend fun deleteAllMyTicker()
}
