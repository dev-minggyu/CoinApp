package com.example.coinapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.tickersymbol.GetTickerSymbolUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    private val getTickerSymbolUseCase: GetTickerSymbolUseCase
) : ViewModel() {
    private val _isSuccessGetTickerSymbol = MutableStateFlow(false)
    val isSuccessGetTickerSymbol = _isSuccessGetTickerSymbol.asStateFlow()

    init {
        getTickerSymbol()
    }

    private fun getTickerSymbol() {
        viewModelScope.launch {
            when (getTickerSymbolUseCase.execute()) {
                is Resource.Success -> _isSuccessGetTickerSymbol.emit(true)
                else -> _isSuccessGetTickerSymbol.emit(false)
            }
        }
    }
}