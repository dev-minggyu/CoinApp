package com.example.data.repository.ticker.remote

import com.example.data.model.ticker.TickerRequest
import com.example.data.model.ticker.TickerResponse
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TickerSocketServiceImpl @Inject constructor(
    private val client: HttpClient
) : TickerSocketService {
    override var socketSession: WebSocketSession? = null

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
        socketSession?.close()
    }

    override fun observeData(): Flow<TickerResponse> {
        val json = Json { ignoreUnknownKeys = true }
        return try {
            socketSession?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Binary }
                ?.map {
                    val text = String((it as Frame.Binary).readBytes())
                    val tickerResponse = json.decodeFromString<TickerResponse>(text)
                    tickerResponse
                } ?: flow {}
        } catch (e: Exception) {
            flow {}
        }
    }
}