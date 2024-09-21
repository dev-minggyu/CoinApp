package com.mingg.coincheck.ui.main

import androidx.lifecycle.ViewModel
import com.mingg.coincheck.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _currentMenuId: MutableStateFlow<Int> = MutableStateFlow(R.id.home_fragment)
    val currentMenuId = _currentMenuId.asStateFlow()
    val onNavItemSelected = { itemId: Int ->
        _currentMenuId.value = itemId
    }
}