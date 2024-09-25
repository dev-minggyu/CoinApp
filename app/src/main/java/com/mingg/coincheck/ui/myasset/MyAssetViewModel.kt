package com.mingg.coincheck.ui.myasset

import androidx.lifecycle.viewModelScope
import com.mingg.coincheck.ui.base.BaseViewModel
import com.mingg.domain.model.myasset.MyTicker
import com.mingg.domain.usecase.myasset.GetMyAssetListUseCase
import com.mingg.domain.usecase.ticker.UnfilteredTickerDataUseCase
import com.mingg.domain.utils.TickerResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAssetViewModel @Inject constructor(
    private val unfilteredTickerDataUseCase: UnfilteredTickerDataUseCase,
    private val getMyAssetListUseCase: GetMyAssetListUseCase
) : BaseViewModel<MyAssetState, MyAssetIntent, MyAssetEffect>() {

    private val assetList: MutableList<MyTicker> = mutableListOf()

    init {
        setEvent(MyAssetIntent.RefreshAssetList)
        setEvent(MyAssetIntent.ObserveTickerList)
    }

    override fun createInitialState(): MyAssetState {
        return MyAssetState(
            myAssetList = null,
            isLoading = false,
            error = null
        )
    }

    override fun handleEvent(event: MyAssetIntent) {
        when (event) {
            is MyAssetIntent.ObserveTickerList -> observeTickerList()
            is MyAssetIntent.RefreshAssetList -> refreshAssetList()
        }
    }

    private fun observeTickerList() {
        viewModelScope.launch {
            unfilteredTickerDataUseCase.execute()
                .collect { tickerResource ->
                    when (tickerResource) {
                        is TickerResource.Update -> {
                            if (assetList.isNotEmpty()) {
                                assetList.forEach { myTicker ->
                                    val updatedTicker = tickerResource.data.tickerList.find { ticker ->
                                        ticker.symbol == myTicker.symbol && ticker.currencyType == myTicker.currencyType
                                    }
                                    myTicker.currentPrice = updatedTicker?.currentPrice ?: "0"
                                }
                                setState { copy(myAssetList = assetList.map { it.copy() }) }
                            }
                        }
                        else -> {}
                    }
                }
        }
    }

    private fun refreshAssetList() {
        viewModelScope.launch {
            try {
                val list = getMyAssetListUseCase.execute()
                if (list.isEmpty()) {
                    assetList.clear()
                    setState { copy(myAssetList = listOf()) }
                } else {
                    assetList.clear()
                    assetList.addAll(list)
                }
            } catch (e: Exception) {
                setState { copy(error = e.message) }
            }
        }
    }
}