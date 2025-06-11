package com.ming.coincheck.ui.setting.floatingwindow.selector

import androidx.lifecycle.viewModelScope
import com.ming.coincheck.ui.base.BaseViewModel
import com.ming.domain.usecase.setting.AllFloatingTickerListUseCase
import com.ming.domain.usecase.setting.SettingFloatingTickerListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FloatingTickerSelectViewModel @Inject constructor(
    private val settingFloatingTickerListUseCase: SettingFloatingTickerListUseCase,
    private val allFloatingTickerListUseCase: AllFloatingTickerListUseCase
) : BaseViewModel<FloatingTickerSelectState, FloatingTickerSelectIntent, FloatingTickerSelectEffect>() {

    override fun createInitialState(): FloatingTickerSelectState {
        return FloatingTickerSelectState()
    }

    override fun handleEvent(event: FloatingTickerSelectIntent) {
        when (event) {
            is FloatingTickerSelectIntent.LoadFloatingTickerList -> loadFloatingSymbolList()
            is FloatingTickerSelectIntent.SetFloatingTickerList -> setFloatingTickerList(event.list)
        }
    }

    private fun loadFloatingSymbolList() {
        viewModelScope.launch {
            val list = allFloatingTickerListUseCase.execute()
            setState { copy(tickerList = list) }
        }
    }

    private fun setFloatingTickerList(list: List<String>) {
        viewModelScope.launch {
            settingFloatingTickerListUseCase.set(list)
        }
    }
}