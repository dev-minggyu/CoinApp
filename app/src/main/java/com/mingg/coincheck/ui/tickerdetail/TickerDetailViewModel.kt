package com.mingg.coincheck.ui.tickerdetail

import androidx.lifecycle.viewModelScope
import com.mingg.coincheck.ui.base.BaseViewModel
import com.mingg.domain.model.ticker.Currency
import com.mingg.domain.usecase.ticker.TickerSingleDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TickerDetailViewModel @Inject constructor(
    private val tickerSingleDataUseCase: TickerSingleDataUseCase
) : BaseViewModel<TickerDetailState, TickerDetailIntent, TickerDetailEffect>() {

    override fun createInitialState(): TickerDetailState {
        return TickerDetailState()
    }

    override fun handleEvent(event: TickerDetailIntent) {
        when (event) {
            is TickerDetailIntent.LoadTicker -> loadTicker(event.symbol, event.currency)
        }
    }

    private fun loadTicker(symbol: String, currency: Currency) {
        viewModelScope.launch {
            tickerSingleDataUseCase.execute(symbol, currency).collect { ticker ->
                setState { copy(ticker = ticker) }
            }
        }
    }
}