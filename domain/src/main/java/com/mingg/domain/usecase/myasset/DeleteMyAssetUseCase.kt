package com.mingg.domain.usecase.myasset

import com.mingg.domain.repository.myasset.MyAssetRepository
import javax.inject.Inject

class DeleteMyAssetUseCase @Inject constructor(private val repository: MyAssetRepository) {
    suspend fun execute(symbol: String, currency: String) =
        repository.deleteMyTicker(symbol, currency)
}