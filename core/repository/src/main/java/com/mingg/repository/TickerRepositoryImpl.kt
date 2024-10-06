package com.mingg.repository

import com.mingg.database.favoriteticker.FavoriteTickerLocalDataSource
import com.mingg.domain.model.ticker.AtomicTickerList
import com.mingg.domain.model.ticker.Currency
import com.mingg.domain.model.ticker.SortModel
import com.mingg.domain.model.ticker.TickerListModel
import com.mingg.domain.repository.ticker.TickerRepository
import com.mingg.domain.utils.Resource
import com.mingg.domain.utils.TickerResource
import com.mingg.network.data.request.TickerRequest
import com.mingg.network.data.response.TickerSymbolResponse
import com.mingg.network.mapper.ticker.TickerSymbolMapper
import com.mingg.network.ticker.TickerSocketService
import com.mingg.network.ticker.TickerSymbolRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TickerRepositoryImpl @Inject constructor(
    private val tickerSocketService: TickerSocketService,
    private val atomicTickerList: AtomicTickerList,
    private val tickerSymbolRemoteDataSource: TickerSymbolRemoteDataSource,
    private val favoriteTickerLocalDataSource: FavoriteTickerLocalDataSource
) : TickerRepository {

    private val _coroutineScope = CoroutineScope(Job() + Dispatchers.Default)

    private val _tickerSocketData = MutableSharedFlow<TickerResource<TickerListModel>>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val tickerSocketData = _tickerSocketData.asSharedFlow()

    private val _tickerSocketUnfilteredData = MutableSharedFlow<TickerResource<TickerListModel>>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val tickerSocketUnfilteredData = _tickerSocketUnfilteredData.asSharedFlow()

    override suspend fun subscribeTicker(receiveDelayMillis: Long): Resource<Unit> =
        withContext(Dispatchers.IO) {
            if (tickerSocketService.isAlreadyOpen()) return@withContext Resource.Success(Unit)
            if (tickerSocketService.openSession()) {
                /**
                 * 서버에 요청할 전체 Symbol 리스트 준비
                 * AtomicTickerList에 한글/영어 Symbol 사전 셋팅
                 */
                val symbolList = mutableListOf<TickerSymbolResponse>()
                when (val symbolResponse = tickerSymbolRemoteDataSource.getTickerSymbolList()) {
                    is Resource.Success -> {
                        for (symbol in symbolResponse.data) {
                            atomicTickerList.updateTicker(
                                TickerSymbolMapper.toTicker(symbol)
                            )
                        }
                        symbolList.addAll(symbolResponse.data)
                    }

                    else -> Resource.Error(null)
                }

                /**
                 * TickerSocketService의 데이터를 옵저빙 하는 작업.
                 * 해당 작업은 withContext()의 Scope가 아닌, 별도의 Scope를 사용해야한다.
                 */
                var requireSizeCheck = true
                tickerSocketService.observeData()
                    .conflate()
                    .onEach { tickerResponse ->
                        when (tickerResponse) {
                            is Resource.Success -> {
                                if (requireSizeCheck) {
                                    if (atomicTickerList.getValidTickerSize() == symbolList.size) {
                                        favoriteTickerLocalDataSource.getFavoriteTickerList().map {
                                            atomicTickerList.updateFavorite(it.symbol, true)
                                        }
                                        requireSizeCheck = false
                                    }
                                } else {
                                    _tickerSocketData.emit(TickerResource.Update(atomicTickerList.getList()))
                                    _tickerSocketUnfilteredData.emit(TickerResource.Update(atomicTickerList.getUnfilteredList())
                                    )
                                    delay(receiveDelayMillis)
                                }
                            }

                            else -> {
                                _tickerSocketData.emit(TickerResource.Error(null))
                                _tickerSocketUnfilteredData.emit(TickerResource.Error(null))
                            }
                        }
                    }.launchIn(_coroutineScope)

                /**
                 * 서버에 전체 Symbol에 대한 데이터 요청
                 */
                tickerSocketService.send(
                    mutableListOf(
                        TickerRequest(
                            codes = symbolList.map {
                                it.market
                            },
                            type = TickerSocketService.REQUEST_TYPE
                        ),
                        TickerRequest(
                            ticket = TickerSocketService.REQUEST_TICKET
                        )
                    )
                )
                return@withContext Resource.Success(Unit)
            } else {
                return@withContext Resource.Error(null)
            }
        }

    override suspend fun unsubscribeTicker() {
        tickerSocketService.closeSession()
        _coroutineScope.cancel()
    }

    override suspend fun sortTickerList(sortModel: SortModel) =
        withContext(Dispatchers.Default) {
            _tickerSocketData.emit(
                TickerResource.Refresh(
                    atomicTickerList.getList(sortModel = sortModel)
                )
            )
        }

    override suspend fun searchTickerList(searchSymbol: String) =
        withContext(Dispatchers.Default) {
            _tickerSocketData.emit(
                TickerResource.Refresh(
                    atomicTickerList.getList(searchSymbol = searchSymbol)
                )
            )
        }

    override suspend fun observeSingleTicker(symbol: String, currency: Currency) = flow {
        tickerSocketData.collect {
            when (it) {
                is TickerResource.Update -> {
                    it.data.tickerList.find { ticker ->
                        ticker.symbol == symbol && ticker.currencyType == currency
                    }?.run {
                        emit(this)
                    }
                }

                else -> {}
            }
        }
    }.flowOn(Dispatchers.Default)
}