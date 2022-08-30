package com.example.data.repository.ticker

import com.example.data.mapper.FavoriteTickerMapper
import com.example.data.model.ticker.AtomicTickerList
import com.example.data.model.ticker.TickerRequest
import com.example.data.provider.TickerMapperProvider
import com.example.data.repository.favoriteticker.local.FavoriteTickerLocalDataSource
import com.example.data.repository.ticker.remote.TickerSocketService
import com.example.data.repository.tickersymbol.local.TickerSymbolLocalDataSource
import com.example.domain.model.ticker.SortModel
import com.example.domain.model.ticker.Ticker
import com.example.domain.repository.ticker.TickerRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class TickerRepositoryImpl @Inject constructor(
    private val tickerSocketService: TickerSocketService,
    private val atomicTickerList: AtomicTickerList,
    private val tickerSymbolLocalDataSource: TickerSymbolLocalDataSource,
    private val favoriteTickerLocalDataSource: FavoriteTickerLocalDataSource,
    private val tickerMapperProvider: TickerMapperProvider
) : TickerRepository {

    private val _coroutineScope = CoroutineScope(Job() + Dispatchers.Default)

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
                favoriteTickerLocalDataSource.getFavoriteTickerList().forEach {
                    atomicTickerList.updateTicker(
                        FavoriteTickerMapper.mapperToTicker(it)
                    )
                }
                tickerSocketService.observeData().onEach { tickerResponse ->
                    when (tickerResponse) {
                        is Resource.Success -> {
                            atomicTickerList.updateTicker(
                                tickerMapperProvider.mapperToTicker(tickerResponse.data)
                            )
                            _tickerSocketData.emit(Resource.Success(atomicTickerList.getList()))
                        }
                        else -> _tickerSocketData.emit(Resource.Error(null))
                    }
                }.launchIn(_coroutineScope)
                tickerSocketService.send(
                    mutableListOf(
                        TickerRequest(
                            codes = tickerSymbolLocalDataSource.getTickerSymbolList().map {
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

    override suspend fun getSortedTickerList(sortModel: SortModel): List<Ticker> =
        withContext(Dispatchers.IO) {
            atomicTickerList.getList(sortModel)
        }

    override suspend fun observeLoadComplete(): Flow<Boolean> = flow {
        val symbolCount = tickerSymbolLocalDataSource.getTickerSymbolList().size
        while (atomicTickerList.getSize() < symbolCount) {
            delay(100)
        }
        emit(true)
    }.flowOn(Dispatchers.Default)
}