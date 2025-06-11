package com.ming.coincheck.ui.myasset.dialog

import androidx.lifecycle.viewModelScope
import com.ming.coincheck.ui.base.BaseViewModel
import com.ming.domain.model.myasset.MyTicker
import com.ming.domain.usecase.myasset.AddMyAssetUseCase
import com.ming.domain.usecase.myasset.DeleteMyAssetUseCase
import com.ming.domain.usecase.myasset.GetMyAssetTickerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMyAssetDialogViewModel @Inject constructor(
    private val getMyAssetTickerUseCase: GetMyAssetTickerUseCase,
    private val addMyAssetUseCase: AddMyAssetUseCase,
    private val deleteMyAssetUseCase: DeleteMyAssetUseCase
) : BaseViewModel<AddMyAssetDialogState, AddMyAssetDialogIntent, AddMyAssetDialogEffect>() {

    override fun createInitialState(): AddMyAssetDialogState {
        return AddMyAssetDialogState()
    }

    override fun handleEvent(event: AddMyAssetDialogIntent) {
        when (event) {
            is AddMyAssetDialogIntent.CheckMyAssetTicker -> checkMyAssetTicker(event.symbol, event.currency)
            is AddMyAssetDialogIntent.AddAsset -> addAsset(event.myTicker)
            is AddMyAssetDialogIntent.DeleteAsset -> deleteAsset(event.symbol, event.currency)
        }
    }

    private fun checkMyAssetTicker(symbol: String, currency: String) {
        viewModelScope.launch {
            val ticker = getMyAssetTickerUseCase.execute(symbol, currency)
            setState { copy(myAssetTicker = ticker) }
        }
    }

    private fun addAsset(myTicker: MyTicker) {
        viewModelScope.launch {
            addMyAssetUseCase.execute(myTicker)
        }
    }

    private fun deleteAsset(symbol: String, currency: String) {
        viewModelScope.launch {
            deleteMyAssetUseCase.execute(symbol, currency)
        }
    }
}