package com.example.data.model.ticker

import com.example.domain.model.ticker.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class AtomicTickerListImpl @Inject constructor() : AtomicTickerList {
    override val mutex = Mutex()

    override var list = mutableListOf<Ticker>()

    override var sortModel = SortModel(SortCategory.VOLUME, SortType.NO)

    override suspend fun updateTicker(element: Ticker) {
        mutex.withLock {
            list.find {
                (it.symbol == element.symbol) && (it.currencyType == element.currencyType)
            }?.apply {
                currentPrice = element.currentPrice
                decimalCurrentPrice = element.decimalCurrentPrice
                prevPrice = element.prevPrice
                rate = element.rate
                volume = element.volume
                dividedVolume = element.dividedVolume
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

    override suspend fun getList(): List<Ticker> = mutex.withLock {
        sortList(sortModel)
        list.map {
            it.copy()
        }
    }

    override suspend fun getList(sortModel: SortModel): List<Ticker> = mutex.withLock {
        this.sortModel = sortModel
        sortList(sortModel)
        list.map {
            it.copy()
        }
    }

    override fun sortList(sortModel: SortModel) {
        this.sortModel = sortModel
        list.apply {
            when (sortModel.type) {
                SortType.NO ->
                    sortByDescending { it.volume.toFloat() }
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

    override fun getSize(): Int = list.size

    override suspend fun clear() {
        mutex.withLock {
            list.clear()
        }
    }
}