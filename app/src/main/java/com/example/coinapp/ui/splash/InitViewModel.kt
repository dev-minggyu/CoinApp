package com.example.coinapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.tickerlist.GetTickerListUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    private val getTickerListUseCase: GetTickerListUseCase
) : ViewModel() {
    private val _isSuccessGetTickerList = MutableStateFlow(false)
    val isSuccessGetTickerList = _isSuccessGetTickerList.asStateFlow()

    init {
        getTickerList()
    }

    private fun getTickerList() {
        viewModelScope.launch {
            when (getTickerListUseCase.execute()) {
                is Resource.Success -> _isSuccessGetTickerList.emit(true)
                else -> _isSuccessGetTickerList.emit(false)
            }
        }
    }
}