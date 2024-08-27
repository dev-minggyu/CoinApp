package com.mingg.domain.usecase.myasset

import com.mingg.domain.repository.myasset.MyAssetRepository
import javax.inject.Inject

class GetMyAssetListUseCase @Inject constructor(private val repository: MyAssetRepository) {
    suspend fun execute() = repository.getMyAssetList()
}