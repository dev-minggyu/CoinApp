package com.example.domain.usecase

import com.example.domain.repository.ticker.TickerRepository
import com.example.domain.utils.Resource
import javax.inject.Inject

class GetAllTickersUseCase @Inject constructor(private val repository: TickerRepository) {
    suspend fun execute(): Resource<Unit> = repository.getAllTickers()
}