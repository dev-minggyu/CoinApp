package com.example.domain.repository.tickerlist

import com.example.domain.utils.Resource

interface TickerListRepository {
    suspend fun getTickerList(): Resource<Unit>
}