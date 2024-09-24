package com.mingg.coincheck.ui.home

import androidx.lifecycle.viewModelScope
import com.mingg.coincheck.App
import com.mingg.coincheck.R
import com.mingg.coincheck.ui.base.BaseViewModel
import com.mingg.domain.model.ticker.SortModel
import com.mingg.domain.usecase.favoriteticker.FavoriteTickerUseCase
import com.mingg.domain.usecase.setting.SettingFloatingWindowUseCase
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
    private val unsubscribeTickerUseCase: UnsubscribeTickerUseCase,
    private val settingFloatingWindowUseCase: SettingFloatingWindowUseCase
) : BaseViewModel<HomeState, HomeEvent, HomeEffect>() {

    override fun createInitialState(): HomeState {
        return HomeState()
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadTickers -> loadTickers()
            is HomeEvent.InsertFavorite -> insertFavoriteTicker(event.symbol)
            is HomeEvent.DeleteFavorite -> deleteFavoriteTicker(event.symbol)
            is HomeEvent.Search -> searchTickers(event.query)
            is HomeEvent.Sort -> sortTickers(event.sortModel)
            is HomeEvent.Subscribe -> subscribeTicker()
            is HomeEvent.Unsubscribe -> unsubscribeTicker()
        }
    }

    private fun loadTickers() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            tickerDataUseCase.execute().collect { resource ->
                when (resource) {
                    is TickerResource.Update -> {
                        setState {
                            copy(
                                loading = false,
                                tickerList = resource.data.tickerList,
                                sortModel = resource.data.sortModel
                            )
                        }
                    }

                    is TickerResource.Refresh -> {
                        setState {
                            copy(
                                loading = false,
                                tickerList = resource.data.tickerList,
                                sortModel = resource.data.sortModel
                            )
                        }
                    }

                    is TickerResource.Error -> {
                        setState {
                            copy(
                                loading = false,
                                error = App.getString(R.string.error_network)
                            )
                        }
                    }
                }
            }
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
            setState { copy(searchText = query) }
            tickerSearchUseCase.execute(query)
        }
    }

    private fun sortTickers(sortModel: SortModel) {
        viewModelScope.launch {
            setState { copy(sortModel = sortModel) }
            tickerSortUseCase.execute(sortModel)
        }
    }

    private fun subscribeTicker() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            when (subscribeTickerUseCase.execute(300L)) {
                is Resource.Success -> setState { copy(error = null) }
                is Resource.Error -> {
                    setState {
                        copy(
                            loading = false,
                            error = App.getString(R.string.error_network)
                        )
                    }
                }

                else -> {}
            }
        }
    }

    private fun unsubscribeTicker() {
        viewModelScope.launch {
            unsubscribeTickerUseCase.execute()
        }
    }
}
