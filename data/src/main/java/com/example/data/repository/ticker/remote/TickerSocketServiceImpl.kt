package com.example.data.repository.ticker.remote

import com.example.data.model.ticker.TickerRequest
import com.example.data.model.ticker.TickerResponse
import com.example.data.provider.TickerMapperProvider
import com.example.domain.model.ticker.AtomicTickerList
import com.example.domain.utils.Resource
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TickerSocketServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val atomicTickerList: AtomicTickerList,
    private val tickerMapperProvider: TickerMapperProvider
) : TickerSocketService {
    override var socketSession: WebSocketSession? = null

    override fun isAlreadyOpen(): Boolean = socketSession == null

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
                    atomicTickerList.updateTicker(
                        tickerMapperProvider.mapperToTicker(tickerResponse)
                    )
                    emit(Resource.Success(Unit))
                }
            }
        } catch (e: Exception) {
            closeSession()
            emit(Resource.Error(null))
        }
    }
}