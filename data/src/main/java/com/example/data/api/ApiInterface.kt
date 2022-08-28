package com.example.data.api

import com.example.data.model.tickerlist.TickerListResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("v1/market/all?isDetails=false")
    suspend fun getTickerList(): Response<List<TickerListResponse>>

    companion object {
        const val BASE_URL = "https://api.upbit.com"
    }
}