package com.example.data.repository.ticker

import com.example.data.mapper.TickerMapper
import com.example.data.model.ticker.AtomicTickerList
import com.example.data.model.ticker.TickerRequest
import com.example.data.repository.ticker.remote.TickerSocketService
import com.example.data.repository.tickerlist.local.TickerListLocalDataSource
import com.example.domain.model.ticker.Ticker
import com.example.domain.repository.ticker.TickerRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TickerRepositoryImpl @Inject constructor(
    private val tickerSocketService: TickerSocketService,
    private val atomicTickerList: AtomicTickerList,
    private val tickerListLocalDataSource: TickerListLocalDataSource
) : TickerRepository {

    override fun observeTicker(): Flow<Resource<List<Ticker>>> = flow {
        if (tickerSocketService.openSession()) {
            tickerSocketService.send(
                mutableListOf(
                    TickerRequest(
                        codes = tickerListLocalDataSource.getTickerList().map {
                            it.market
                        },
                        type = TickerSocketService.REQUEST_TYPE
                    ),
                    TickerRequest(
                        ticket = TickerSocketService.REQUEST_TICKET
                    )
                )
            )
            tickerSocketService.observeData().collect {
                atomicTickerList.updateTicker(
                    TickerMapper.mapperToTicker(it)
                )
                emit(Resource.Success(atomicTickerList.getList()))
            }
        } else {
            emit(Resource.Error(null))
        }
    }.flowOn(Dispatchers.IO)
}