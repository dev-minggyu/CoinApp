package com.mingg.coincheck.ui.home

import com.mingg.coincheck.ui.base.UiEffect
import com.mingg.coincheck.ui.base.UiEvent
import com.mingg.coincheck.ui.base.UiState
import com.mingg.domain.model.ticker.SortModel
import com.mingg.domain.model.ticker.Ticker

sealed class HomeEvent : UiEvent {
    data object LoadTickers : HomeEvent()
    data class InsertFavorite(val symbol: String) : HomeEvent()
    data class DeleteFavorite(val symbol: String) : HomeEvent()
    data class Search(val query: String) : HomeEvent()
    data class Sort(val sortModel: SortModel) : HomeEvent()
    data object Subscribe : HomeEvent()
    data object Unsubscribe : HomeEvent()
}

data class HomeState(
    val loading: Boolean = false,
    val tickerList: List<Ticker>? = null,
    val error: String? = null,
    val searchText: String? = null,
    val sortModel: SortModel? = null
) : UiState

sealed class HomeEffect : UiEffect