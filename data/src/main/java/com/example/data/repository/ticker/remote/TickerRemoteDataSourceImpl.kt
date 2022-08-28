package com.example.data.repository.ticker.remote

import com.example.data.api.ApiService
import com.example.data.model.ticker.TickerResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TickerRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) : TickerRemoteDataSource {
    override fun observeTickerList(): Flow<List<TickerResponse>> {
        TODO("Not yet implemented")
    }
}