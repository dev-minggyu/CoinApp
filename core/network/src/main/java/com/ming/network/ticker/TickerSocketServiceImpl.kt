package com.ming.network.ticker

import com.ming.domain.model.ticker.AtomicTickerList
import com.ming.domain.utils.Resource
import com.ming.network.data.request.TickerRequest
import com.ming.network.data.response.TickerResponse
import com.ming.network.mapper.ticker.TickerResponseMapper
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readBytes
import io.ktor.websocket.send
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TickerSocketServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val atomicTickerList: AtomicTickerList,
    private val tickerResponseMapper: TickerResponseMapper
) : TickerSocketService {
    override var socketSession: WebSocketSession? = null

    override fun isAlreadyOpen(): Boolean = socketSession != null

    override suspend fun openSession(): Boolean {
        return try {
            socketSession = client.webSocketSession {
                url(TickerSocketService.BASE_URL)
            }
            socketSession?.isActive == true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun send(tickerRequest: List<TickerRequest>) {
        socketSession?.send(Json.encodeToString(tickerRequest))
    }

    override suspend fun closeSession() {
        try {
            socketSession?.close()
            socketSession = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeData(): Flow<Resource<Unit>> = flow {
        val json = Json { ignoreUnknownKeys = true }
        try {
            socketSession?.incoming?.consumeEach { frame ->
                if (socketSession == null) {
                    atomicTickerList.clear()
                    throw Exception()
                }
                if (frame is Frame.Binary) {
                    val tickerResponse = json.decodeFromString<TickerResponse>(String(frame.readBytes()))
                    atomicTickerList.updateTicker(tickerResponseMapper.mapperToTicker(tickerResponse))
                    emit(Resource.Success(Unit))
                }
            }
        } catch (e: Exception) {
            closeSession()
            emit(Resource.Error(e))
        }
    }
}