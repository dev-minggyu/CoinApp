package com.example.coinapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ticker.SortModel
import com.example.domain.model.ticker.Ticker
import com.example.domain.usecase.favoriteticker.FavoriteTickerUseCase
import com.example.domain.usecase.ticker.TickerDataUseCase
import com.example.domain.usecase.ticker.TickerSearchUseCase
import com.example.domain.usecase.ticker.TickerSortUseCase
import com.example.domain.utils.TickerResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tickerDataUseCase: TickerDataUseCase,
    private val tickerSortUseCase: TickerSortUseCase,
    private val tickerSearchUseCase: TickerSearchUseCase,
    private val favoriteTickerUseCase: FavoriteTickerUseCase
) : ViewModel() {
    private val _tickerList: MutableStateFlow<List<Ticker>?> = MutableStateFlow(null)
    val tickerList: StateFlow<List<Ticker>?>
        get() = _tickerList

    val searchText: MutableStateFlow<String?> = MutableStateFlow(null)

    val sortModel: MutableStateFlow<SortModel?> = MutableStateFlow(null)

    val onSortChanged = { sortModel: SortModel ->
        sortTickerList(sortModel)
    }

    init {
        observeTickerList()
        observeSearchText()
    }

    private fun observeTickerList() {
        viewModelScope.launch {
            tickerDataUseCase.execute()
                .collect {
                    when (it) {
                        is TickerResource.Update -> {
                            _tickerList.value = it.data.tickerList
                            sortModel.value = it.data.sortModel
                        }
                        is TickerResource.Refresh -> {
                            _tickerList.value = null
                            _tickerList.value = it.data.tickerList
                            sortModel.value = it.data.sortModel
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

    private fun sortTickerList(sortModel: SortModel) {
        viewModelScope.launch {
            tickerSortUseCase.execute(sortModel)
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
}