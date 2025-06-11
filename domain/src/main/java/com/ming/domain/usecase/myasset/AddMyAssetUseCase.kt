package com.ming.domain.usecase.myasset

import com.ming.domain.model.myasset.MyTicker
import com.ming.domain.repository.myasset.MyAssetRepository
import javax.inject.Inject

class AddMyAssetUseCase @Inject constructor(private val repository: MyAssetRepository) {
    suspend fun execute(ticker: MyTicker) =
        repository.insertMyTicker(ticker)
}