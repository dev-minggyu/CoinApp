package com.example.coinapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.coinapp.enums.SortCategory
import com.example.coinapp.enums.SortModel
import com.example.coinapp.enums.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    val searchText: MutableStateFlow<String> = MutableStateFlow("")

    val sortModel: MutableStateFlow<SortModel> = MutableStateFlow(SortModel(SortCategory.NAME, SortType.NO))
    val onTickerSortClick = { sortModel: SortModel ->
        test(sortModel)
    }

    fun test(sortModel: SortModel) {
        this.sortModel.value = sortModel
    }
}