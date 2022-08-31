package com.example.domain.usecase.ticker

import com.example.domain.model.ticker.SortModel
import com.example.domain.repository.ticker.TickerRepository
import javax.inject.Inject

class TickerSortUseCase @Inject constructor(private val repository: TickerRepository) {
    suspend fun execute(sortModel: SortModel) = repository.sortTickerList(sortModel)
}