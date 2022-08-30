package com.example.coinapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ticker.SortCategory
import com.example.domain.model.ticker.SortModel
import com.example.domain.model.ticker.SortType
import com.example.domain.model.ticker.Ticker
import com.example.domain.usecase.favoriteticker.FavoriteTickerUseCase
import com.example.domain.usecase.ticker.TickerDataUseCase
import com.example.domain.usecase.ticker.TickerSearchUseCase
import com.example.domain.usecase.ticker.TickerSortUseCase
import com.example.domain.utils.Resource
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
    private val favoriteTickerUseCase: FavoriteTickerUseCase
) : ViewModel() {
    private val _tickerList: MutableStateFlow<List<Ticker>?> = MutableStateFlow(listOf())
    val tickerList = _tickerList.asStateFlow()

    val searchText: MutableStateFlow<String> = MutableStateFlow("")

    val sortModel: MutableStateFlow<SortModel> = MutableStateFlow(SortModel(SortCategory.NAME, SortType.NO))

    val sortTickerList = { sortModel: SortModel ->
        this.sortModel.value = sortModel.copy()
        sortTickerList(sortModel)
    }

    init {
        observeTickerList()
        observeSearchText()
    }

    private fun observeTickerList() {
        viewModelScope.launch {
            tickerDataUseCase.execute().collect {
                when (it) {
                    is Resource.Success -> {
                        _tickerList.value = it.data
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
            _tickerList.value = tickerSortUseCase.execute(sortModel)
        }
    }

    private fun observeSearchText() {
        viewModelScope.launch {
            searchText.collect {
                _tickerList.value = tickerSearchUseCase.execute(it)
            }
        }
    }
}
