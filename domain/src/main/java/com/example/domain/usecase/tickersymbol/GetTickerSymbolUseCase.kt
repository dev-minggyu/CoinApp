package com.example.domain.usecase.tickersymbol

import com.example.domain.repository.tickersymbol.TickerSymbolRepository
import com.example.domain.utils.Resource
import javax.inject.Inject

class GetTickerSymbolUseCase @Inject constructor(private val repository: TickerSymbolRepository) {
    suspend fun execute(): Resource<Unit> = repository.getTickerSymbolList()
}