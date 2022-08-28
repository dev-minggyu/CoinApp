package com.example.data.repository.ticker.remote

import com.example.data.api.ApiInterface
import com.example.data.model.ticker.TickerResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TickerRemoteDataSourceImpl @Inject constructor(private val apiInterface: ApiInterface) : TickerRemoteDataSource {
    override fun observeTickerList(): Flow<List<TickerResponse>> {
        TODO("Not yet implemented")
    }
}