package com.example.coinapp.ui.myasset.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinapp.model.MyTickerInfo
import com.example.domain.model.myasset.MyTicker
import com.example.domain.usecase.myasset.AddMyAssetUseCase
import com.example.domain.usecase.myasset.GetMyAssetTickerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMyAssetDialogViewModel @Inject constructor(
    private val getMyAssetTickerUseCase: GetMyAssetTickerUseCase,
    private val addMyAssetUseCase: AddMyAssetUseCase
) : ViewModel() {
    private val _myAssetTicker: MutableStateFlow<MyTicker?> = MutableStateFlow(null)
    val myAssetTicker = _myAssetTicker.asStateFlow()

    fun checkMyAssetTicker(ticker: MyTickerInfo) {
        viewModelScope.launch {
            _myAssetTicker.value = getMyAssetTickerUseCase.execute(
                MyTickerInfo.toMyTicker(ticker)
            )
        }
    }

    fun addAsset(myTicker: MyTicker) {
        viewModelScope.launch {
            addMyAssetUseCase.execute(myTicker)
        }
    }
}