package com.mingg.coincheck.ui.setting.floatingwindow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mingg.domain.model.setting.FloatingTicker
import com.mingg.domain.usecase.setting.AllFloatingTickerListUseCase
import com.mingg.domain.usecase.setting.SettingFloatingTickerListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FloatingSymbolSelectViewModel @Inject constructor(
    private val settingFloatingTickerListUseCase: SettingFloatingTickerListUseCase,
    private val allFloatingTickerListUseCase: AllFloatingTickerListUseCase
) : ViewModel() {
    private val _symbolList: MutableStateFlow<List<FloatingTicker>?> =
        MutableStateFlow(null)
    val symbolList = _symbolList.asStateFlow()

    fun getFloatingSymbolList() {
        viewModelScope.launch {
            _symbolList.value = allFloatingTickerListUseCase.execute()
        }
    }

    fun setFloatingTickerList(list: List<String>) {
        settingFloatingTickerListUseCase.set(list)
    }
}