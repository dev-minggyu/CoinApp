package com.ming.database.myasset

import com.ming.database.data.myasset.MyAssetDao
import com.ming.database.data.myasset.MyTickerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyAssetLocalDataSourceImpl @Inject constructor(
    private val myAssetDao: MyAssetDao
) : MyAssetLocalDataSource {
    override suspend fun getMyAssetList(): List<MyTickerEntity> =
        withContext(Dispatchers.IO) {
            myAssetDao.getMyAssetList()
        }

    override suspend fun getMyAssetTicker(symbol: String, currency: String): MyTickerEntity? =
        withContext(Dispatchers.IO) {
            myAssetDao.getMyAssetTicker(symbol, currency)
        }

    override suspend fun insertMyTicker(tickerEntity: MyTickerEntity) {
        withContext(Dispatchers.IO) {
            myAssetDao.insertMyTicker(tickerEntity)
        }
    }

    override suspend fun deleteMyTicker(symbol: String, currency: String) {
        withContext(Dispatchers.IO) {
            myAssetDao.deleteMyTicker(symbol, currency)
        }
    }

    override suspend fun deleteAllMyTicker() {
        withContext(Dispatchers.IO) {
            myAssetDao.deleteAllMyTicker()
        }
    }
}
