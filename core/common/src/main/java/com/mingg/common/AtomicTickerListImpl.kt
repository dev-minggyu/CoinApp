package com.mingg.common

import com.mingg.common.extension.updateOrInsert
import com.mingg.domain.model.ticker.AtomicTickerList
import com.mingg.domain.model.ticker.SortCategory
import com.mingg.domain.model.ticker.SortModel
import com.mingg.domain.model.ticker.SortType
import com.mingg.domain.model.ticker.Ticker
import com.mingg.domain.model.ticker.TickerListModel
import com.mingg.domain.model.ticker.TickerSymbol
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class AtomicTickerListImpl @Inject constructor() : AtomicTickerList {
    private val _list = mutableListOf<Ticker>()
    override val list: List<Ticker>
        get() = _list.toList()

    private val mutex = Mutex()

    private var sortModel = SortModel(SortCategory.VOLUME, SortType.DESC)

    private var searchSymbol: String = ""

    override suspend fun updateTicker(element: Ticker) {
        mutex.withLock {
            _list.updateOrInsert(
                predicate = { it.symbol == element.symbol && it.currencyType == element.currencyType },
                update = {
                    copy(
                        currentPrice = element.currentPrice,
                        decimalCurrentPrice = element.decimalCurrentPrice,
                        changePricePrevDay = element.changePricePrevDay,
                        rate = element.rate,
                        volume = element.volume,
                        formattedVolume = element.formattedVolume,
                        isVolumeDividedByMillion = element.isVolumeDividedByMillion
                    )
                },
                insert = element
            )
        }
    }

    override suspend fun updateFavorite(symbol: String, isFavorite: Boolean) {
        mutex.withLock {
            val splitSymbol = symbol.split("-")
            _list.updateOrInsert(
                predicate = { it.symbol == splitSymbol[1] && it.currencyType.name == splitSymbol[0] },
                update = { copy(isFavorite = isFavorite) },
                insert = null
            )
        }
    }

    override suspend fun getList(sortModel: SortModel?, searchSymbol: String?) = mutex.withLock {
        sortModel?.let {
            this.sortModel = sortModel
        }
        searchSymbol?.let {
            this.searchSymbol = searchSymbol
        }

        sortList()

        var result = copyTickerList()
        if (this.searchSymbol.isNotEmpty()) {
            result = result.filter {
                it.symbol.startsWith(this.searchSymbol, true)
                        || it.koreanSymbol.startsWith(this.searchSymbol)
            }
        }

        TickerListModel(result, this.sortModel.copy())
    }

    override suspend fun getUnfilteredList(): TickerListModel = mutex.withLock {
        TickerListModel(copyTickerList(), this.sortModel.copy())
    }

    override fun sortList() {
        _list.apply {
            when (sortModel.type) {
                SortType.DESC ->
                    when (sortModel.category) {
                        SortCategory.NAME -> sortByDescending { it.symbol }
                        SortCategory.PRICE -> sortByDescending { it.currentPrice.toFloat() }
                        SortCategory.RATE -> sortByDescending { it.rate.toFloat() }
                        SortCategory.VOLUME -> sortByDescending { it.volume.toFloat() }
                    }

                SortType.ASC ->
                    when (sortModel.category) {
                        SortCategory.NAME -> sortBy { it.symbol }
                        SortCategory.PRICE -> sortBy { it.currentPrice.toFloat() }
                        SortCategory.RATE -> sortBy { it.rate.toFloat() }
                        SortCategory.VOLUME -> sortBy { it.volume.toFloat() }
                    }
            }
        }
    }

    override fun getValidTickerSize(): Int = _list.count { it.currentPrice.isNotEmpty() }

    override suspend fun getSymbolList(): List<TickerSymbol> = mutex.withLock {
        copyTickerList().sortedBy { it.symbol }.map { it.toTickerSymbol() }
    }

    override suspend fun clear() {
        mutex.withLock { _list.clear() }
    }

    private fun copyTickerList(): List<Ticker> = _list.map { it.copy() }

    private fun Ticker.toTickerSymbol(): TickerSymbol =
        TickerSymbol(
            symbol = symbol,
            currency = currencyType,
            koreanSymbol = koreanSymbol,
            englishSymbol = englishSymbol
        )
}