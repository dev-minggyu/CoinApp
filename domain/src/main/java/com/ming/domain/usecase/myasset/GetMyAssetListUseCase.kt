package com.ming.domain.usecase.myasset

import com.ming.domain.repository.myasset.MyAssetRepository
import javax.inject.Inject

class GetMyAssetListUseCase @Inject constructor(private val repository: MyAssetRepository) {
    suspend fun execute() = repository.getMyAssetList()
}