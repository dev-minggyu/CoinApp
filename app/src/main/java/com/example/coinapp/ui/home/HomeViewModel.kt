package com.example.coinapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinapp.enums.SortCategory
import com.example.coinapp.enums.SortModel
import com.example.coinapp.enums.SortType
import com.example.domain.model.ticker.Ticker
import com.example.domain.usecase.favoriteticker.FavoriteTickerUseCase
import com.example.domain.usecase.ticker.SubscribeTickerUseCase
import com.example.domain.usecase.ticker.TickerDataUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val subscribeTickerUseCase: SubscribeTickerUseCase,
    private val tickerDataUseCase: TickerDataUseCase,
    private val favoriteTickerUseCase: FavoriteTickerUseCase
) : ViewModel() {

    init {
        observeTickerList()
        subscribeTicker()
    }

    private val _tickerList: MutableStateFlow<List<Ticker>?> = MutableStateFlow(null)
    val tickerList = _tickerList.asStateFlow()

    val searchText: MutableStateFlow<String> = MutableStateFlow("")

    val sortModel: MutableStateFlow<SortModel> = MutableStateFlow(SortModel(SortCategory.NAME, SortType.NO))
    val onTickerSortClick = { sortModel: SortModel ->

    }

    private fun subscribeTicker() {
        viewModelScope.launch {
            subscribeTickerUseCase.execute()
        }
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
}