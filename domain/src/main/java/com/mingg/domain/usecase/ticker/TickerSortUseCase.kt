package com.mingg.domain.usecase.ticker

import com.mingg.domain.model.ticker.SortModel
import com.mingg.domain.repository.ticker.TickerRepository
import javax.inject.Inject

class TickerSortUseCase @Inject constructor(private val repository: TickerRepository) {
    suspend fun execute(sortModel: SortModel) =
        repository.sortTickerList(sortModel)
}