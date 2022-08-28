package com.example.data.repository.tickerlist.remote

import com.example.data.api.ApiInterface
import com.example.data.model.tickerlist.TickerListResponse
import com.example.domain.utils.Resource
import javax.inject.Inject

class TickerListRemoteDataSourceImpl @Inject constructor(private val apiInterface: ApiInterface) : TickerListRemoteDataSource {
    override suspend fun getTickerList(): Resource<List<TickerListResponse>> =
        try {
            val response = apiInterface.getTickerList()
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message!!)
        }
}