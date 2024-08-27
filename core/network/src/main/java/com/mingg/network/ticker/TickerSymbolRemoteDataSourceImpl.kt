package com.mingg.network.ticker

import com.mingg.common.callApi
import com.mingg.domain.utils.Resource
import com.mingg.network.data.response.TickerSymbolResponse
import com.mingg.network.api.ApiService
import javax.inject.Inject

class TickerSymbolRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    TickerSymbolRemoteDataSource {
    override suspend fun getTickerSymbolList(): Resource<List<TickerSymbolResponse>> =
        callApi {
            apiService.getTickerSymbolList()
        }
}