package com.mingg.coincheck.ui.main

import androidx.lifecycle.viewModelScope
import com.mingg.coincheck.ui.base.BaseViewModel
import com.mingg.domain.usecase.setting.SettingTickerChangeColorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareSettingViewModel @Inject constructor(
    private val settingTickerChangeColorUseCase: SettingTickerChangeColorUseCase
) : BaseViewModel<ShareSettingState, ShareSettingIntent, ShareSettingEffect>() {

    init {
        loadSettings()
    }

    override fun createInitialState(): ShareSettingState {
        return ShareSettingState()
    }

    override fun handleEvent(event: ShareSettingIntent) {
        when (event) {
            is ShareSettingIntent.LoadSettings -> loadSettings()
            is ShareSettingIntent.SetChangeTickerColor -> setChangeTickerColor(event.value)
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