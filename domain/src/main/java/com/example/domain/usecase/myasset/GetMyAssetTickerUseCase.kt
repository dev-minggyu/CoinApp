package com.example.domain.usecase.myasset

import com.example.domain.model.myasset.MyTicker
import com.example.domain.repository.myasset.MyAssetRepository
import javax.inject.Inject

class GetMyAssetTickerUseCase @Inject constructor(private val repository: MyAssetRepository) {
    suspend fun execute(ticker: MyTicker) =
        repository.getMyAssetTicker(ticker)
}