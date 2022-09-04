package com.example.coinapp.ui.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ticker.Currency
import com.example.domain.model.ticker.Ticker
import com.example.domain.usecase.detail.TickerDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TickerDetailViewModel @Inject constructor(
    private val tickerDetailUseCase: TickerDetailUseCase
) : ViewModel() {
    private val _ticker: MutableStateFlow<Ticker?> = MutableStateFlow(null)
    val ticker = _ticker.asStateFlow()

    fun observeTicker(symbol: String, currency: Currency) {
        viewModelScope.launch {
            tickerDetailUseCase.execute(symbol, currency).collect {
                _ticker.value = it
            }
        }
    }
}