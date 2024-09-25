package com.mingg.coincheck.ui.main

import androidx.lifecycle.viewModelScope
import com.mingg.coincheck.ui.base.BaseViewModel
import com.mingg.domain.usecase.setting.SettingTickerChangeColorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedSettingViewModel @Inject constructor(
    private val settingTickerChangeColorUseCase: SettingTickerChangeColorUseCase
) : BaseViewModel<SharedSettingState, SharedSettingIntent, SharedSettingEffect>() {

    init {
        loadSettings()
    }

    override fun createInitialState(): SharedSettingState {
        return SharedSettingState()
    }

    override fun handleEvent(event: SharedSettingIntent) {
        when (event) {
            is SharedSettingIntent.LoadSettings -> loadSettings()
            is SharedSettingIntent.SetChangeTickerColor -> setChangeTickerColor(event.value)
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            val changeColor = settingTickerChangeColorUseCase.get()
            setState { copy(tickerChangeColor = changeColor) }
        }
    }

    private fun setChangeTickerColor(value: Boolean) {
        viewModelScope.launch {
            settingTickerChangeColorUseCase.set(value)
            setState { copy(tickerChangeColor = value) }
        }
    }
}