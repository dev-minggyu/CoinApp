package com.example.data.repository.ticker

import com.example.data.model.ticker.TickerEntity
import com.example.data.repository.ticker.local.TickerLocalDataSource
import com.example.data.repository.ticker.remote.TickerRemoteDataSource
import com.example.domain.repository.ticker.TickerRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TickerRepositoryImpl @Inject constructor(
    private val tickerRemoteDataSource: TickerRemoteDataSource,
    private val tickerLocalDataSource: TickerLocalDataSource,
) : TickerRepository {
    override suspend fun getAllTickers(): Resource<Unit> =
        withContext(Dispatchers.IO) {
            tickerLocalDataSource.deleteAllTickers()
            when (val tickerListResponse = tickerRemoteDataSource.getAllTickers()) {
                is Resource.Success -> {
                    tickerLocalDataSource.insertTickers(tickerListResponse.data.map {
                        TickerEntity(
                            market = it.market,
                            englishName = it.englishName,
                            koreanName = it.koreanName
                        )
                    })
                    Resource.Success(Unit)
                }
                else -> {
                    Resource.Error(null)
                }
            }
        }
}