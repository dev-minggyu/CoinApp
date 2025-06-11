package com.ming.coincheck.ui.myasset

import com.ming.coincheck.ui.base.UiEffect
import com.ming.coincheck.ui.base.UiIntent
import com.ming.coincheck.ui.base.UiState
import com.ming.domain.model.myasset.MyTicker

sealed class MyAssetIntent : UiIntent {
    data object ObserveTickerList : MyAssetIntent()
}

data class MyAssetState(
    val myAssetList: List<MyTicker>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState

sealed class MyAssetEffect : UiEffect