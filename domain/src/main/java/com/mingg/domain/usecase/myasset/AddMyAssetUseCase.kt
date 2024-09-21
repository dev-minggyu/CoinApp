package com.mingg.domain.usecase.myasset

import com.mingg.domain.model.myasset.MyTicker
import com.mingg.domain.repository.myasset.MyAssetRepository
import javax.inject.Inject

class AddMyAssetUseCase @Inject constructor(private val repository: MyAssetRepository) {
    suspend fun execute(ticker: MyTicker) =
        repository.insertMyTicker(ticker)
}