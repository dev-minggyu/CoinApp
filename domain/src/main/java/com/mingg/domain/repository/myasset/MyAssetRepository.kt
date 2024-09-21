package com.mingg.domain.repository.myasset

import com.mingg.domain.model.myasset.MyTicker

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