package com.example.data.repository.ticker

import com.example.data.model.ticker.AtomicTickerList
import com.example.data.model.ticker.TickerRequest
import com.example.data.provider.TickerMapperProvider
import com.example.data.repository.ticker.remote.TickerSocketService
import com.example.data.repository.tickerlist.local.TickerListLocalDataSource
import com.example.domain.model.ticker.Ticker
import com.example.domain.repository.ticker.TickerRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TickerRepositoryImpl @Inject constructor(
    private val tickerSocketService: TickerSocketService,
    private val atomicTickerList: AtomicTickerList,
    private val tickerListLocalDataSource: TickerListLocalDataSource,
    private val tickerMapperProvider: TickerMapperProvider
) : TickerRepository {

    private val _tickerSocketData = MutableSharedFlow<Resource<List<Ticker>>>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val tickerSocketData = _tickerSocketData.asSharedFlow()

    override suspend fun subscribeTicker(): Resource<Unit> =
        withContext(Dispatchers.IO) {
            if (tickerSocketService.isAlreadyOpen()) Resource.Success(Unit)
            if (tickerSocketService.openSession()) {
                tickerSocketService.observeData().onEach {
                    when (it) {
                        is Resource.Success -> {
                            atomicTickerList.updateTicker(
                                tickerMapperProvider.mapperToTicker(it.data)
                            )
                            _tickerSocketData.emit(Resource.Success(atomicTickerList.getList()))
                        }
                        else -> _tickerSocketData.emit(Resource.Error(null))
                    }
                }.launchIn(this)
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
                Resource.Success(Unit)
            } else {
                Resource.Error(null)
            }
        }

    override suspend fun unsubscribeTicker() {
        tickerSocketService.closeSession()
    }
}