package com.example.data.repository.ticker

import com.example.data.model.ticker.TickerRequest
import com.example.data.model.ticker.TickerSymbolResponse
import com.example.data.repository.favoriteticker.local.FavoriteTickerLocalDataSource
import com.example.data.repository.ticker.remote.TickerSocketService
import com.example.data.repository.ticker.remote.TickerSymbolRemoteDataSource
import com.example.domain.model.ticker.AtomicTickerList
import com.example.domain.model.ticker.SortModel
import com.example.domain.model.ticker.TickerListModel
import com.example.domain.repository.ticker.TickerRepository
import com.example.domain.utils.Resource
import com.example.domain.utils.TickerResource
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class TickerRepositoryImpl @Inject constructor(
    private val tickerSocketService: TickerSocketService,
    private val atomicTickerList: AtomicTickerList,
    private val tickerSymbolRemoteDataSource: TickerSymbolRemoteDataSource,
    private val favoriteTickerLocalDataSource: FavoriteTickerLocalDataSource
) : TickerRepository {

    private val _coroutineScope = CoroutineScope(Job() + Dispatchers.Default)

    private val _tickerSocketData = MutableSharedFlow<TickerResource<TickerListModel>>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val tickerSocketData = _tickerSocketData.asSharedFlow()

    override suspend fun subscribeTicker(receiveDelayMillis: Long): Resource<Unit> =
        withContext(Dispatchers.IO) {
            if (tickerSocketService.isAlreadyOpen()) return@withContext Resource.Success(Unit)
            if (tickerSocketService.openSession()) {
                /**
                 * 서버에 요청할 전체 Symbol 리스트 준비
                 */
                val symbolList = mutableListOf<TickerSymbolResponse>()
                when (val symbolResponse = tickerSymbolRemoteDataSource.getTickerSymbolList()) {
                    is Resource.Success -> {
                        symbolList.addAll(symbolResponse.data)
                    }
                    else -> Resource.Error(null)
                }

                /**
                 * TickerSocketService의 데이터를 옵저빙 하는 작업.
                 * 해당 작업은 withContext()의 Scope가 아닌, 별도의 Scope를 사용해야한다.
                 */
                var requireFavoriteSetup = true
                tickerSocketService.observeData()
                    .conflate()
                    .onEach { tickerResponse ->
                        when (tickerResponse) {
                            is Resource.Success -> {
                                if (atomicTickerList.getSize() == symbolList.size) {
                                    if (requireFavoriteSetup) {
                                        favoriteTickerLocalDataSource.getFavoriteTickerList().map {
                                            atomicTickerList.updateFavorite(it.symbol, true)
                                        }
                                        requireFavoriteSetup = false
                                    }
                                    _tickerSocketData.emit(TickerResource.Update(atomicTickerList.getList()))
                                    delay(receiveDelayMillis)
                                }
                            }
                            else -> {
                                _tickerSocketData.emit(TickerResource.Error(null))
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
}