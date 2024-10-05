package com.mingg.coincheck.ui.home

import com.mingg.coincheck.ui.base.UiEffect
import com.mingg.coincheck.ui.base.UiIntent
import com.mingg.coincheck.ui.base.UiState
import com.mingg.domain.model.ticker.SortModel
import com.mingg.domain.model.ticker.Ticker

sealed class HomeIntent : UiIntent {
    data object LoadTickers : HomeIntent()
    data class InsertFavorite(val symbol: String) : HomeIntent()
    data class DeleteFavorite(val symbol: String) : HomeIntent()
    data class Search(val query: String) : HomeIntent()
    data class Sort(val sortModel: SortModel) : HomeIntent()
    data object Subscribe : HomeIntent()
    data object Unsubscribe : HomeIntent()
}

data class HomeState(
    val isLoading: Boolean = false,
    val tickerList: List<Ticker>? = null,
    val error: HomeErrorType? = null,
    val searchText: String? = null,
    val sortModel: SortModel? = null
) : UiState

sealed class HomeEffect : UiEffect

sealed class HomeErrorType {
    data object NetworkError : HomeErrorType()
    data object UnexpectedError : HomeErrorType()
}