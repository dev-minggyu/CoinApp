package com.mingg.coincheck.ui.home

import androidx.lifecycle.viewModelScope
import com.mingg.coincheck.ui.base.BaseViewModel
import com.mingg.domain.model.ticker.SortModel
import com.mingg.domain.usecase.favoriteticker.FavoriteTickerUseCase
import com.mingg.domain.usecase.ticker.SubscribeTickerUseCase
import com.mingg.domain.usecase.ticker.TickerDataUseCase
import com.mingg.domain.usecase.ticker.TickerSearchUseCase
import com.mingg.domain.usecase.ticker.TickerSortUseCase
import com.mingg.domain.usecase.ticker.UnsubscribeTickerUseCase
import com.mingg.domain.utils.Resource
import com.mingg.domain.utils.TickerResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tickerDataUseCase: TickerDataUseCase,
    private val tickerSortUseCase: TickerSortUseCase,
    private val tickerSearchUseCase: TickerSearchUseCase,
    private val favoriteTickerUseCase: FavoriteTickerUseCase,
    private val subscribeTickerUseCase: SubscribeTickerUseCase,
    private val unsubscribeTickerUseCase: UnsubscribeTickerUseCase
) : BaseViewModel<HomeState, HomeIntent, HomeEffect>() {

    override fun createInitialState(): HomeState {
        return HomeState()
    }

    override fun handleEvent(event: HomeIntent) {
        when (event) {
            is HomeIntent.LoadTickers -> loadTickers()
            is HomeIntent.InsertFavorite -> insertFavoriteTicker(event.symbol)
            is HomeIntent.DeleteFavorite -> deleteFavoriteTicker(event.symbol)
            is HomeIntent.Search -> searchTickers(event.query)
            is HomeIntent.Sort -> sortTickers(event.sortModel)
            is HomeIntent.Subscribe -> subscribeTicker()
            is HomeIntent.Unsubscribe -> unsubscribeTicker()
        }
    }

    private fun setHomeError(homeErrorType: HomeErrorType) {
        setState {
            copy(
                isLoading = false,
                error = homeErrorType
            )
        }
    }

    private fun loadTickers() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            tickerDataUseCase.execute().collect { resource ->
                when (resource) {
                    is TickerResource.Refresh -> {
                        setState {
                            copy(
                                isLoading = false,
                                error = null,
                                tickerList = resource.data.tickerList,
                                sortModel = resource.data.sortModel
                            )
                        }
                    }

                    is TickerResource.Update -> {
                        setState {
                            copy(
                                isLoading = false,
                                error = null,
                                tickerList = resource.data.tickerList,
                                sortModel = resource.data.sortModel
                            )
                        }
                    }

                    is TickerResource.Error -> setHomeError(HomeErrorType.NetworkError)
                }
            }
        }
    }

    private fun subscribeTicker() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            when (subscribeTickerUseCase.execute(300L)) {
                is Resource.Success -> setState { copy(error = null) }
                is Resource.Error -> setHomeError(HomeErrorType.NetworkError)
                else -> {}
            }
        }
    }

    private fun unsubscribeTicker() {
        viewModelScope.launch {
            unsubscribeTickerUseCase.execute()
        }
    }

    private fun insertFavoriteTicker(symbol: String) {
        viewModelScope.launch {
            favoriteTickerUseCase.executeInsert(symbol)
        }
    }

    private fun deleteFavoriteTicker(symbol: String) {
        viewModelScope.launch {
            favoriteTickerUseCase.executeDelete(symbol)
        }
    }

    private fun searchTickers(query: String) {
        viewModelScope.launch {
            setState { copy(searchText = query, error = null) }
            tickerSearchUseCase.execute(query)
        }
    }

    private fun sortTickers(sortModel: SortModel) {
        viewModelScope.launch {
            setState { copy(sortModel = sortModel, error = null) }
            tickerSortUseCase.execute(sortModel)
        }
    }
}