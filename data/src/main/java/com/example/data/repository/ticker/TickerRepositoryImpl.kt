package com.example.data.repository.ticker

import com.example.data.repository.ticker.local.TickerLocalDataSource
import com.example.data.repository.ticker.remote.TickerRemoteDataSource
import com.example.domain.model.ticker.Ticker
import com.example.domain.repository.ticker.TickerRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TickerRepositoryImpl @Inject constructor(
    private val tickerRemoteDataSource: TickerRemoteDataSource,
    private val tickerLocalDataSource: TickerLocalDataSource,
) : TickerRepository {
    override fun observeTicker(): Flow<Resource<Ticker>> {
        TODO("Not yet implemented")
    }

}