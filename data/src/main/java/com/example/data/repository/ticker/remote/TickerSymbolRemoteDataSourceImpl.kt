package com.example.data.repository.ticker.remote

import com.example.data.api.ApiService
import com.example.data.model.ticker.TickerSymbolResponse
import com.example.domain.utils.Resource
import javax.inject.Inject

class TickerSymbolRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) : TickerSymbolRemoteDataSource {
    override suspend fun getTickerSymbolList(): Resource<List<TickerSymbolResponse>> =
        try {
            val response = apiService.getTickerSymbolList()
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message!!)
        }
}