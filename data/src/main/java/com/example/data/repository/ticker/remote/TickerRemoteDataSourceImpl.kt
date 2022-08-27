package com.example.data.repository.ticker.remote

import com.example.data.api.ApiInterface
import com.example.data.model.ticker.TickerResponse
import com.example.domain.utils.Resource
import javax.inject.Inject

class TickerRemoteDataSourceImpl @Inject constructor(private val apiInterface: ApiInterface) : TickerRemoteDataSource {
    override suspend fun getAllTickers(): Resource<List<TickerResponse>> =
        try {
            val response = apiInterface.getAllTickers()
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message!!)
        }
}