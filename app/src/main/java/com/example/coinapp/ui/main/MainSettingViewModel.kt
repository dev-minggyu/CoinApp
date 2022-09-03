package com.example.coinapp.ui.main

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.setting.SettingTickerChangeColorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainSettingViewModel @Inject constructor(
    private val settingTickerChangeColorUseCase: SettingTickerChangeColorUseCase
) : ViewModel() {
    private val _tickerChangeColor = MutableStateFlow(true)
    val tickerChangeColor = _tickerChangeColor.asStateFlow()

    fun loadSettings() {
        _tickerChangeColor.value = settingTickerChangeColorUseCase.get()
    }

    fun setTickerChangeColor(value: Boolean) {
        settingTickerChangeColorUseCase.set(value)
        _tickerChangeColor.value = value
    }
}