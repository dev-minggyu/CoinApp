package com.mingg.coincheck.ui.myasset

import com.mingg.coincheck.ui.base.UiEffect
import com.mingg.coincheck.ui.base.UiIntent
import com.mingg.coincheck.ui.base.UiState
import com.mingg.domain.model.myasset.MyTicker

sealed class MyAssetIntent : UiIntent {
    object ObserveTickerList : MyAssetIntent()
    object RefreshAssetList : MyAssetIntent()
}

data class MyAssetState(
    val myAssetList: List<MyTicker>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState

sealed class MyAssetEffect : UiEffect