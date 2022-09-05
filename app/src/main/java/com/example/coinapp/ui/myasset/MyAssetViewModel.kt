package com.example.coinapp.ui.myasset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.myasset.MyTicker
import com.example.domain.usecase.myasset.GetMyAssetListUseCase
import com.example.domain.usecase.ticker.TickerDataUseCase
import com.example.domain.utils.TickerResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAssetViewModel @Inject constructor(
    private val tickerDataUseCase: TickerDataUseCase,
    private val getMyAssetListUseCase: GetMyAssetListUseCase
) : ViewModel() {
    private val _myAssetList: MutableStateFlow<List<MyTicker>?> = MutableStateFlow(null)
    val myAssetList = _myAssetList.asStateFlow()

    private var _assetList: MutableList<MyTicker> = mutableListOf()

    init {
        observeTickerList()
    }

    private fun observeTickerList() {
        viewModelScope.launch {
            tickerDataUseCase.execute()
                .collect { tickerResource ->
                    when (tickerResource) {
                        is TickerResource.Update -> {
                            if (_assetList.isNotEmpty()) {
                                _assetList.forEach { myTicker ->
                                    myTicker.currentPrice = tickerResource.data.tickerList.find { ticker ->
                                        ticker.symbol == myTicker.symbol && ticker.currencyType == myTicker.currencyType
                                    }?.currentPrice ?: "0"
                                }
                                _myAssetList.value = _assetList.map {
                                    it.copy()
                                }
                            }
                        }
                        else -> {}
                    }
                }
        }
    }

    fun refreshAssetList() {
        viewModelScope.launch {
            val list = getMyAssetListUseCase.execute()
            if (list.isEmpty()) {
                _assetList.clear()
                _myAssetList.value = listOf()
            } else {
                _assetList.clear()
                list.forEach { myTicker ->
                    _assetList.find {
                        it.symbol == myTicker.symbol && it.currencyType == myTicker.currencyType
                    }?.apply {
                        amount = myTicker.amount
                        averagePrice = myTicker.averagePrice
                    } ?: run {
                        _assetList.add(myTicker)
                    }
                }
            }
        }
    }
}