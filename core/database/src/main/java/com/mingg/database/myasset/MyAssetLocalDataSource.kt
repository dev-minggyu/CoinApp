package com.mingg.database.myasset

import com.mingg.database.data.myasset.MyTickerEntity

interface MyAssetLocalDataSource {
    suspend fun getMyAssetList(): List<MyTickerEntity>

    suspend fun getMyAssetTicker(symbol: String, currency: String): MyTickerEntity?

    suspend fun insertMyTicker(tickerEntity: MyTickerEntity)

    suspend fun deleteMyTicker(symbol: String, currency: String)

    suspend fun deleteAllMyTicker()
}
