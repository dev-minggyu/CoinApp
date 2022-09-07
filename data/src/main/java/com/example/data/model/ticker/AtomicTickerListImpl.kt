package com.example.data.model.ticker

import com.example.domain.model.ticker.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class AtomicTickerListImpl @Inject constructor() : AtomicTickerList {
    override val mutex = Mutex()

    override var list = mutableListOf<Ticker>()

    override var sortModel = SortModel(SortCategory.VOLUME, SortType.DESC)

    override var searchSymbol: String = ""

    override suspend fun updateTicker(element: Ticker) {
        mutex.withLock {
            list.find {
                (it.symbol == element.symbol) && (it.currencyType == element.currencyType)
            }?.apply {
                currentPrice = element.currentPrice
                decimalCurrentPrice = element.decimalCurrentPrice
                changePricePrevDay = element.changePricePrevDay
                rate = element.rate
                volume = element.volume
                formattedVolume = element.formattedVolume
            } ?: run {
                list.add(element)
            }
        }
    }

    override suspend fun updateFavorite(symbol: String, isFavorite: Boolean) {
        mutex.withLock {
            val splitSymbol = symbol.split("-")
            list.find {
                (it.symbol == splitSymbol[1]) && (it.currencyType.name == splitSymbol[0])
            }?.apply {
                this.isFavorite = isFavorite
            }
        }
    }

    override suspend fun getList(sortModel: SortModel?, searchSymbol: String?): TickerListModel = mutex.withLock {
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

    private fun copyTickerList(): List<Ticker> =
        list.map {
            it.copy()
        }

    override fun sortList() {
        list.apply {
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

    override fun getValidTickerSize(): Int =
        list.count {
            it.currentPrice.isNotEmpty()
        }

    override suspend fun getSymbolList(): List<FloatingTicker> = mutex.withLock {
        val result = copyTickerList().sortedBy { it.symbol }
        result.map {
            FloatingTicker(
                symbol = it.symbol + it.currencyType.name,
                koreanSymbol = it.koreanSymbol,
                englishSymbol = it.englishSymbol,
                isChecked = false
            )
        }
    }

    override suspend fun clear() {
        mutex.withLock {
            list.clear()
        }
    }
}