package com.example.coinapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetAllTickersUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    private val getAllTickersUseCase: GetAllTickersUseCase
) : ViewModel() {
    private val _isSuccessGetAllTickers = MutableStateFlow(false)
    val isSuccessGetAllTickers = _isSuccessGetAllTickers.asStateFlow()

    init {
        getAllTickers()
    }

    private fun getAllTickers() {
        viewModelScope.launch {
            when (getAllTickersUseCase.execute()) {
                is Resource.Success -> _isSuccessGetAllTickers.emit(true)
                else -> _isSuccessGetAllTickers.emit(false)
            }
        }
    }
}