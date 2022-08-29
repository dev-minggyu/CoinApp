package com.example.coinapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinapp.enums.SortCategory
import com.example.coinapp.enums.SortModel
import com.example.coinapp.enums.SortType
import com.example.domain.model.ticker.Ticker
import com.example.domain.usecase.ticker.TickerListUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tickerListUseCase: TickerListUseCase
) : ViewModel() {
    private val _tickerList: MutableStateFlow<List<Ticker>?> = MutableStateFlow(null)
    val tickerList = _tickerList.asStateFlow()

    val searchText: MutableStateFlow<String> = MutableStateFlow("")

    val sortModel: MutableStateFlow<SortModel> = MutableStateFlow(SortModel(SortCategory.NAME, SortType.NO))
    val onTickerSortClick = { sortModel: SortModel ->
    }

    init {
        viewModelScope.launch {
            tickerListUseCase.execute().collect {
                when (it) {
                    is Resource.Success -> {
                        _tickerList.value = it.data
                    }
                    else -> {}
                }
            }
        }
    }
}