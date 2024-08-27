package com.mingg.network.ticker

import com.mingg.domain.utils.Resource
import com.mingg.network.data.request.TickerRequest
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
        const val REQUEST_TICKET = "ticket_com.mingg.coinapp"
        const val REQUEST_TYPE = "ticker"
    }
}