package com.example.data.repository.tickerlist

import com.example.data.model.tickerlist.TickerListEntity
import com.example.data.repository.tickerlist.local.TickerListLocalDataSource
import com.example.data.repository.tickerlist.remote.TickerListRemoteDataSource
import com.example.domain.repository.tickerlist.TickerListRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TickerListRepositoryImpl @Inject constructor(
    private val tickerListRemoteDataSource: TickerListRemoteDataSource,
    private val tickerListLocalDataSource: TickerListLocalDataSource,
) : TickerListRepository {
    override suspend fun getTickerList(): Resource<Unit> =
        withContext(Dispatchers.IO) {
            tickerListLocalDataSource.deleteTickerList()
            when (val tickerListResponse = tickerListRemoteDataSource.getTickerList()) {
                is Resource.Success -> {
                    tickerListLocalDataSource.insertTickerList(tickerListResponse.data.map {
                        TickerListEntity(
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