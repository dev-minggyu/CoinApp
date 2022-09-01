package com.example.domain.model.ticker

import kotlinx.coroutines.sync.Mutex

interface AtomicTickerList {
    val mutex: Mutex

    var list: MutableList<Ticker>

    var sortModel: SortModel

    var searchSymbol: String

    suspend fun updateTicker(element: Ticker)

    suspend fun updateFavorite(symbol: String, isFavorite: Boolean)

    suspend fun getList(sortModel: SortModel? = null, searchSymbol: String? = null): List<Ticker>

    fun sortList()

    fun getSize(): Int

    suspend fun clear()
}