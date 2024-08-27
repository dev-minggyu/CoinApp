package com.mingg.coinapp.ui.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mingg.domain.model.ticker.Currency
import com.mingg.domain.model.ticker.Ticker
import com.mingg.domain.usecase.ticker.TickerSingleDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TickerDetailViewModel @Inject constructor(
    private val tickerSingleDataUseCase: TickerSingleDataUseCase
) : ViewModel() {
    private val _ticker: MutableStateFlow<Ticker?> =
        MutableStateFlow(null)
    val ticker = _ticker.asStateFlow()

    fun observeTicker(symbol: String, currency: Currency) {
        viewModelScope.launch {
            tickerSingleDataUseCase.execute(symbol, currency).collect {
                _ticker.value = it
            }
        }
    }
}