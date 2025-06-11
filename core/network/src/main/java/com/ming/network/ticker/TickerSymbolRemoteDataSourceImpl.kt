package com.ming.network.ticker

import com.ming.common.callApi
import com.ming.domain.utils.Resource
import com.ming.network.data.response.TickerSymbolResponse
import com.ming.network.api.ApiService
import javax.inject.Inject

class TickerSymbolRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    TickerSymbolRemoteDataSource {
    override suspend fun getTickerSymbolList(): Resource<List<TickerSymbolResponse>> =
        callApi {
            apiService.getTickerSymbolList()
        }
}