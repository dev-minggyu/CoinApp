package com.mingg.coinapp.ui.myasset.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mingg.domain.model.myasset.MyTicker
import com.mingg.domain.usecase.myasset.AddMyAssetUseCase
import com.mingg.domain.usecase.myasset.DeleteMyAssetUseCase
import com.mingg.domain.usecase.myasset.GetMyAssetTickerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMyAssetDialogViewModel @Inject constructor(
    private val getMyAssetTickerUseCase: GetMyAssetTickerUseCase,
    private val addMyAssetUseCase: AddMyAssetUseCase,
    private val deleteMyAssetUseCase: DeleteMyAssetUseCase
) : ViewModel() {
    private val _myAssetTicker: MutableStateFlow<MyTicker?> =
        MutableStateFlow(null)
    val myAssetTicker = _myAssetTicker.asStateFlow()

    fun checkMyAssetTicker(symbol: String, currency: String) {
        viewModelScope.launch {
            _myAssetTicker.value = getMyAssetTickerUseCase.execute(symbol, currency)
        }
    }

    fun addAsset(myTicker: MyTicker) {
        viewModelScope.launch {
            addMyAssetUseCase.execute(myTicker)
        }
    }

    fun deleteAsset(symbol: String, currency: String) {
        viewModelScope.launch {
            deleteMyAssetUseCase.execute(symbol, currency)
        }
    }
}