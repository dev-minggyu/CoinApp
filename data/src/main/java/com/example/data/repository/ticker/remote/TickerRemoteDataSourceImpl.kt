package com.example.data.repository.ticker.remote

import com.example.data.api.ApiInterface
import javax.inject.Inject

class TickerRemoteDataSourceImpl @Inject constructor(private val apiInterface: ApiInterface) : TickerRemoteDataSource {

}