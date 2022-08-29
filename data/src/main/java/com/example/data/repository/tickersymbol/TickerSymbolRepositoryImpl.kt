package com.example.data.repository.tickersymbol

import com.example.data.model.tickersymbol.TickerSymbolEntity
import com.example.data.repository.tickersymbol.local.TickerSymbolLocalDataSource
import com.example.data.repository.tickersymbol.remote.TickerSymbolRemoteDataSource
import com.example.domain.repository.tickersymbol.TickerSymbolRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TickerSymbolRepositoryImpl @Inject constructor(
    private val tickerSymbolRemoteDataSource: TickerSymbolRemoteDataSource,
    private val tickerSymbolLocalDataSource: TickerSymbolLocalDataSource,
) : TickerSymbolRepository {
    override suspend fun getTickerSymbolList(): Resource<Unit> =
        withContext(Dispatchers.IO) {
            tickerSymbolLocalDataSource.deleteTickerSymbolList()
            when (val tickerSymbolListResponse = tickerSymbolRemoteDataSource.getTickerSymbolList()) {
                is Resource.Success -> {
                    tickerSymbolLocalDataSource.insertTickerSymbolList(tickerSymbolListResponse.data.map {
                        TickerSymbolEntity(
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