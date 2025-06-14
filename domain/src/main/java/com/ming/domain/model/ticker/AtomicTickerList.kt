package com.ming.domain.model.ticker

interface AtomicTickerList {
    val list: List<Ticker>

    suspend fun updateTicker(element: Ticker)

    suspend fun updateFavorite(symbol: String, isFavorite: Boolean)

    suspend fun getList(sortModel: SortModel? = null, searchSymbol: String? = null): TickerListModel

    suspend fun getUnfilteredList(): TickerListModel

    fun sortList()

    fun getValidTickerSize(): Int

    suspend fun getSymbolList(): List<TickerSymbol>

    suspend fun clear()
}