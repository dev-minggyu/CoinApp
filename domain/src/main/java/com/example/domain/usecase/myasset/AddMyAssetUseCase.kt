package com.example.domain.usecase.myasset

import com.example.domain.model.myasset.MyTicker
import com.example.domain.repository.myasset.MyAssetRepository
import javax.inject.Inject

class AddMyAssetUseCase @Inject constructor(private val repository: MyAssetRepository) {
    suspend fun execute(ticker: MyTicker) =
        repository.insertMyTicker(ticker)
}