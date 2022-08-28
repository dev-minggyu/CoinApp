package com.example.data.repository.ticker.remote

import com.example.data.model.ticker.TickerRequest
import com.example.data.model.ticker.TickerResponse
import io.ktor.websocket.*
import kotlinx.coroutines.flow.Flow

interface TickerSocketService {
    var socketSession: WebSocketSession?

    suspend fun openSession(): Boolean

    suspend fun send(tickerRequest: List<TickerRequest>)

    suspend fun closeSession()

    fun observeData(): Flow<TickerResponse>

    companion object {
        const val BASE_URL = "wss://api.upbit.com/websocket/v1"
        const val REQUEST_TICKET = "ticket_com.example.coinapp"
        const val REQUEST_TYPE = "ticker"
    }
}