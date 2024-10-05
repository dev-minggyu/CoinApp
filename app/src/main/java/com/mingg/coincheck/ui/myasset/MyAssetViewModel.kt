package com.mingg.coincheck.ui.myasset

import androidx.lifecycle.viewModelScope
import com.mingg.coincheck.ui.base.BaseViewModel
import com.mingg.domain.model.myasset.MyTicker
import com.mingg.domain.model.ticker.Ticker
import com.mingg.domain.usecase.myasset.GetMyAssetListUseCase
import com.mingg.domain.usecase.ticker.UnfilteredTickerDataUseCase
import com.mingg.domain.utils.TickerResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAssetViewModel @Inject constructor(
    private val unfilteredTickerDataUseCase: UnfilteredTickerDataUseCase,
    private val getMyAssetListUseCase: GetMyAssetListUseCase
) : BaseViewModel<MyAssetState, MyAssetIntent, MyAssetEffect>() {

    private val assetList: MutableList<MyTicker> = mutableListOf()

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
        }
    }

    private fun observeTickerList() {
        viewModelScope.launch {
            loadAssetList()
            unfilteredTickerDataUseCase.execute()
                .collect { tickerResource ->
                    when (tickerResource) {
                        is TickerResource.Update -> {
                            updateAssetList(tickerResource.data.tickerList)
                        }

                        else -> {}
                    }
                }
        }
    }

    private suspend fun loadAssetList() = coroutineScope {
        try {
            val list = getMyAssetListUseCase.execute()
            if (list.isEmpty()) {
                assetList.clear()
                setState { copy(myAssetList = listOf()) }
            } else {
                for (newItem in list) {
                    val existingItemIndex = assetList.indexOfFirst { it.symbol == newItem.symbol && it.currencyType == newItem.currencyType }
                    if (existingItemIndex != -1) {
                        assetList[existingItemIndex].apply {
                            amount = newItem.amount
                            averagePrice = newItem.averagePrice
                        }
                    } else {
                        assetList.add(newItem)
                    }
                }
            }
        } catch (e: Exception) {
            setState { copy(error = e.message) }
        }
    }

    private fun updateAssetList(tickerList: List<Ticker>) {
        if (assetList.isNotEmpty()) {
            assetList.forEach { myTicker ->
                val updatedTicker = tickerList.find { ticker ->
                    ticker.symbol == myTicker.symbol && ticker.currencyType == myTicker.currencyType
                }
                myTicker.currentPrice = updatedTicker?.currentPrice ?: "0"
            }
            setState { copy(myAssetList = assetList.map { it.copy() }) }
        }
    }
}