package com.ming.network.api

import com.ming.network.data.response.TickerSymbolResponse
import retrofit2.http.GET

interface ApiService {
    @GET("v1/market/all?isDetails=false")
    suspend fun getTickerSymbolList(): List<TickerSymbolResponse>

    companion object {
        const val BASE_URL = "https://api.upbit.com"
    }
}