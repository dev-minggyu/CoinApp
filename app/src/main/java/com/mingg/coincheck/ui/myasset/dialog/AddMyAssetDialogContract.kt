package com.mingg.coincheck.ui.myasset.dialog

import com.mingg.coincheck.ui.base.UiEffect
import com.mingg.coincheck.ui.base.UiIntent
import com.mingg.coincheck.ui.base.UiState
import com.mingg.domain.model.myasset.MyTicker

sealed class AddMyAssetDialogIntent : UiIntent {
    data class CheckMyAssetTicker(val symbol: String, val currency: String) : AddMyAssetDialogIntent()
    data class AddAsset(val myTicker: MyTicker) : AddMyAssetDialogIntent()
    data class DeleteAsset(val symbol: String, val currency: String) : AddMyAssetDialogIntent()
}

data class AddMyAssetDialogState(
    val myAssetTicker: MyTicker? = null
) : UiState

sealed class AddMyAssetDialogEffect : UiEffect