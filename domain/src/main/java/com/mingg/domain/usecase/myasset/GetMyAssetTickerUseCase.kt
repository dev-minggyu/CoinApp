package com.mingg.domain.usecase.myasset

import com.mingg.domain.repository.myasset.MyAssetRepository
import javax.inject.Inject

class GetMyAssetTickerUseCase @Inject constructor(private val repository: MyAssetRepository) {
    suspend fun execute(symbol: String, currency: String) =
        repository.getMyAssetTicker(symbol, currency)
}