package com.example.coinapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor() : ViewModel() {
    private val _isLoadCompleted = MutableStateFlow(false)
    val isLoadCompleted = _isLoadCompleted.asStateFlow()

    init {
        loading()
    }

    private fun loading() {
        viewModelScope.launch {
            _isLoadCompleted.value = true
        }
    }
}