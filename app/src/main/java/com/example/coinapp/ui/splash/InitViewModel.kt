package com.example.coinapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.ticker.SubscribeTickerUseCase
import com.example.domain.usecase.tickersymbol.GetTickerSymbolUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    private val getTickerSymbolUseCase: GetTickerSymbolUseCase,
    private val subscribeTickerUseCase: SubscribeTickerUseCase
) : ViewModel() {
    private val _isLoadCompleted = MutableStateFlow(false)
    val isLoadCompleted = _isLoadCompleted.asStateFlow()

    init {
        getTickerSymbol()
    }

    private fun getTickerSymbol() {
        viewModelScope.launch {
            when (getTickerSymbolUseCase.execute()) {
                is Resource.Success -> {
                    subscribeTicker()
                }
                else -> {}
            }
        }
    }

    private fun subscribeTicker() {
        viewModelScope.launch {
            subscribeTickerUseCase.execute(300L)
            _isLoadCompleted.value = true
        }
    }
}