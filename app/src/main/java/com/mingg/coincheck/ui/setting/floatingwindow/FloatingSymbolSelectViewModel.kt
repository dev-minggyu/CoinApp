package com.mingg.coincheck.ui.setting.floatingwindow

import androidx.lifecycle.viewModelScope
import com.mingg.coincheck.ui.base.BaseViewModel
import com.mingg.domain.usecase.setting.AllFloatingTickerListUseCase
import com.mingg.domain.usecase.setting.SettingFloatingTickerListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FloatingSymbolSelectViewModel @Inject constructor(
    private val settingFloatingTickerListUseCase: SettingFloatingTickerListUseCase,
    private val allFloatingTickerListUseCase: AllFloatingTickerListUseCase
) : BaseViewModel<FloatingSymbolSelectState, FloatingSymbolSelectIntent, FloatingSymbolSelectEffect>() {

    override fun createInitialState(): FloatingSymbolSelectState {
        return FloatingSymbolSelectState()
    }

    override fun handleEvent(event: FloatingSymbolSelectIntent) {
        when (event) {
            is FloatingSymbolSelectIntent.LoadFloatingSymbolList -> loadFloatingSymbolList()
            is FloatingSymbolSelectIntent.SetFloatingTickerList -> setFloatingTickerList(event.list)
        }
    }

    private fun loadFloatingSymbolList() {
        viewModelScope.launch {
            val list = allFloatingTickerListUseCase.execute()
            setState { copy(symbolList = list) }
        }
    }

    private fun setFloatingTickerList(list: List<String>) {
        viewModelScope.launch {
            settingFloatingTickerListUseCase.set(list)
        }
    }
}