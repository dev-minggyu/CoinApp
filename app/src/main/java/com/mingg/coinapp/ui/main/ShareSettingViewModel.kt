package com.mingg.coinapp.ui.main

import androidx.lifecycle.ViewModel
import com.mingg.domain.usecase.setting.SettingTickerChangeColorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ShareSettingViewModel @Inject constructor(
    private val settingTickerChangeColorUseCase: SettingTickerChangeColorUseCase
) : ViewModel() {
    private val _tickerChangeColor = MutableStateFlow(true)
    val tickerChangeColor = _tickerChangeColor.asStateFlow()

    fun loadSettings() {
        _tickerChangeColor.value = getChangeTickerColor()
    }

    fun setChangeTickerColor(value: Boolean) {
        settingTickerChangeColorUseCase.set(value)
        _tickerChangeColor.value = value
    }

    fun getChangeTickerColor() = settingTickerChangeColorUseCase.get()
}