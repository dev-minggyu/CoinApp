package com.example.data.repository.ticker

import com.example.data.repository.ticker.local.TickerLocalDataSource
import com.example.data.repository.ticker.remote.TickerRemoteDataSource
import com.example.domain.repository.ticker.TickerRepository
import javax.inject.Inject

class TickerRepositoryImpl @Inject constructor(
    private val tickerRemoteDataSource: TickerRemoteDataSource,
    private val tickerLocalDataSource: TickerLocalDataSource,
) : TickerRepository {

}