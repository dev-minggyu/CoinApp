package com.example.network.ticker

import com.example.common.callApi
import com.example.domain.utils.Resource
import com.example.network.data.response.TickerSymbolResponse
import com.example.network.api.ApiService
import javax.inject.Inject

class TickerSymbolRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    TickerSymbolRemoteDataSource {
    override suspend fun getTickerSymbolList(): Resource<List<TickerSymbolResponse>> =
        callApi {
            apiService.getTickerSymbolList()
        }
}