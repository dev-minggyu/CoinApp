package com.ming.domain.usecase.ticker

import com.ming.domain.model.ticker.SortModel
import com.ming.domain.repository.ticker.TickerRepository
import javax.inject.Inject

class TickerSortUseCase @Inject constructor(private val repository: TickerRepository) {
    suspend fun execute(sortModel: SortModel) =
        repository.sortTickerList(sortModel)
}