package com.mingg.domain.usecase.ticker

import com.mingg.domain.repository.ticker.TickerRepository
import javax.inject.Inject

class TickerSearchUseCase @Inject constructor(private val repository: TickerRepository) {
    suspend fun execute(searchSymbol: String) = repository.searchTickerList(searchSymbol)
}