package com.ming.network.ticker

import com.ming.domain.utils.Resource
import com.ming.network.data.request.TickerRequest
import io.ktor.websocket.WebSocketSession
import kotlinx.coroutines.flow.Flow

interface TickerSocketService {
    var socketSession: WebSocketSession?

    fun isAlreadyOpen(): Boolean

    suspend fun openSession(): Boolean

    suspend fun send(tickerRequest: List<TickerRequest>)

    suspend fun closeSession()

    fun observeData(): Flow<Resource<Unit>>

    companion object {
        const val BASE_URL = "wss://api.upbit.com/websocket/v1"
        const val REQUEST_TICKET = "ticket_com.ming.coinapp"
        const val REQUEST_TYPE = "ticker"
    }
}