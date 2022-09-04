package com.example.domain.repository.myasset

import com.example.domain.model.myasset.MyTicker

interface MyAssetRepository {
    suspend fun getMyAssetList(): List<MyTicker>

    suspend fun getMyAssetTicker(ticker: MyTicker): MyTicker?

    suspend fun insertMyTicker(ticker: MyTicker)

    suspend fun deleteMyTicker(symbol: String)

    suspend fun deleteAllMyTicker()
}