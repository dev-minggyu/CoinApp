package com.example.data.api

import com.example.data.model.ticker.TickerResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("v1/market/all?isDetails=false")
    suspend fun getAllTickers(): Response<List<TickerResponse>>

    companion object {
        const val BASE_URL = "https://api.upbit.com"
    }
}