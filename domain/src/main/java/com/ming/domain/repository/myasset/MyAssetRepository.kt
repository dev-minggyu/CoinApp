package com.ming.domain.repository.myasset

import com.ming.domain.model.myasset.MyTicker

interface MyAssetRepository {
    suspend fun getMyAssetList(): List<MyTicker>

    suspend fun getMyAssetTicker(
        symbol: String,
        currency: String
    ): MyTicker?

    suspend fun insertMyTicker(ticker: MyTicker)

    suspend fun deleteMyTicker(symbol: String, currency: String)

    suspend fun deleteAllMyTicker()
}