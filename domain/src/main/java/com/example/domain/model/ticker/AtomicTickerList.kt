package com.example.domain.model.ticker

import kotlinx.coroutines.sync.Mutex

interface AtomicTickerList {
    val mutex: Mutex

    var list: MutableList<Ticker>

    var sortModel: SortModel

    suspend fun updateTicker(element: Ticker)

    suspend fun updateFavorite(symbol: String, isFavorite: Boolean)

    suspend fun getList(): List<Ticker>

    suspend fun getList(sortModel: SortModel): List<Ticker>

    fun sortList(sortModel: SortModel)

    fun getSize(): Int

    suspend fun clear()
}