package com.example.domain.usecase.tickerlist

import com.example.domain.repository.tickerlist.TickerListRepository
import com.example.domain.utils.Resource
import javax.inject.Inject

class GetTickerListUseCase @Inject constructor(private val repository: TickerListRepository) {
    suspend fun execute(): Resource<Unit> = repository.getTickerList()
}