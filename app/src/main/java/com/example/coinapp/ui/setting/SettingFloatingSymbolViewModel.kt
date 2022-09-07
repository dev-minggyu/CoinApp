package com.example.coinapp.ui.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ticker.FloatingTicker
import com.example.domain.usecase.setting.SettingFloatingTickerListUseCase
import com.example.domain.usecase.ticker.GetSymbolListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingFloatingSymbolViewModel @Inject constructor(
    private val getSymbolListUseCase: GetSymbolListUseCase,
    private val settingFloatingTickerListUseCase: SettingFloatingTickerListUseCase
) : ViewModel() {
    private val _symbolList: MutableStateFlow<List<FloatingTicker>?> = MutableStateFlow(null)
    val symbolList = _symbolList.asStateFlow()

    fun getSymbolList() {
        viewModelScope.launch {
            val symbolList = getSymbolListUseCase.execute()
            val floatingTickerList = settingFloatingTickerListUseCase.get()
            if (floatingTickerList.isEmpty()) {
                _symbolList.value = symbolList
            } else {
                symbolList.forEach {
                    if (floatingTickerList.contains(it.symbol)) {
                        it.isChecked = true
                    }
                }
                _symbolList.value = symbolList
            }
        }
    }

    fun setFloatingTickerList(list: List<String>) {
        settingFloatingTickerListUseCase.set(list)
    }
}