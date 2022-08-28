package com.example.data.repository.tickerlist.remote

import com.example.data.model.tickerlist.TickerListResponse
import com.example.domain.utils.Resource

interface TickerListRemoteDataSource {
    suspend fun getTickerList(): Resource<List<TickerListResponse>>
}