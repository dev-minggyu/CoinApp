package com.mingg.coinapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mingg.coinapp.App
import com.mingg.coinapp.R
import com.mingg.domain.model.ticker.SortModel
import com.mingg.domain.model.ticker.Ticker
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
) : ViewModel() {
    private val _tickerList: MutableStateFlow<List<Ticker>?> =
        MutableStateFlow(null)
    val tickerList = _tickerList.asStateFlow()

    val searchText: MutableStateFlow<String?> = MutableStateFlow(null)

    val sortModel: MutableStateFlow<SortModel?> =
        MutableStateFlow(null)

    private val _errorTicker: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorTicker = _errorTicker.asStateFlow()

    private val _loadingTicker: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loadingTicker = _loadingTicker.asStateFlow()

    init {
        observeTickerList()
        observeSearchText()
        observeSortModel()
    }

    private fun observeTickerList() {
        viewModelScope.launch {
            tickerDataUseCase.execute()
                .collect {
                    when (it) {
                        is TickerResource.Update -> {
                            _loadingTicker.value = false
                            _tickerList.value = it.data.tickerList
                            sortModel.value = it.data.sortModel
                        }

                        is TickerResource.Refresh -> {
                            _loadingTicker.value = false
                            _tickerList.value = null
                            _tickerList.value = it.data.tickerList
                            sortModel.value = it.data.sortModel
                        }

                        is TickerResource.Error -> {
                            _loadingTicker.value = false
                            _errorTicker.value = App.getString(R.string.error_network)
                        }

                        else -> {}
                    }
                }
        }
    }

    fun insertFavoriteTicker(symbol: String) {
        viewModelScope.launch {
            favoriteTickerUseCase.executeInsert(symbol)
        }
    }

    fun deleteFavoriteTicker(symbol: String) {
        viewModelScope.launch {
            favoriteTickerUseCase.executeDelete(symbol)
        }
    }

    private fun observeSearchText() {
        viewModelScope.launch {
            searchText.collect {
                it?.let {
                    tickerSearchUseCase.execute(it)
                }
            }
        }
    }

    private fun observeSortModel() {
        viewModelScope.launch {
            sortModel.collect {
                it?.let {
                    tickerSortUseCase.execute(it)
                }
            }
        }
    }

    fun subscribeTicker() {
        viewModelScope.launch {
            _loadingTicker.value = true
            when (subscribeTickerUseCase.execute(300L)) {
                is Resource.Success -> _errorTicker.value = null
                is Resource.Error -> {
                    _loadingTicker.value = false
                    _errorTicker.value = App.getString(R.string.error_network)
                }

                else -> {}
            }
        }
    }

    fun unsubscribeTicker() {
        viewModelScope.launch {
            unsubscribeTickerUseCase.execute()
        }
    }
}