package com.example.data.model.ticker

import com.example.domain.model.ticker.SortCategory
import com.example.domain.model.ticker.SortModel
import com.example.domain.model.ticker.SortType
import com.example.domain.model.ticker.Ticker
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AtomicTickerList {
    private val _mutex = Mutex()

    private var _list = mutableListOf<Ticker>()

    private var _sortModel = SortModel(SortCategory.VOLUME, SortType.NO)

    suspend fun updateTicker(element: Ticker) {
        _mutex.withLock {
            _list.find {
                (it.symbol == element.symbol) && (it.currencyType == element.currencyType)
            }?.apply {
                prevPrice = element.prevPrice
                currentPrice = element.currentPrice
                rate = element.rate
                volume = element.volume
            } ?: run {
                _list.add(element)
            }
        }
    }

    suspend fun getList(): List<Ticker> = _mutex.withLock {
        sortList(_sortModel)
        _list.map {
            it.copy()
        }
    }

    suspend fun getList(sortModel: SortModel): List<Ticker> = _mutex.withLock {
        _sortModel = sortModel
        sortList(_sortModel)
        _list.map {
            it.copy()
        }
    }

    private fun sortList(sortModel: SortModel) {
        _sortModel = sortModel
        _list.apply {
            when (_sortModel.type) {
                SortType.NO ->
                    sortByDescending { it.volume.toFloat() }
                SortType.DESC ->
                    when (_sortModel.category) {
                        SortCategory.NAME -> sortByDescending { it.symbol }
                        SortCategory.PRICE -> sortByDescending { it.currentPrice.toFloat() }
                        SortCategory.RATE -> sortByDescending { it.rate.toFloat() }
                        SortCategory.VOLUME -> sortByDescending { it.volume.toFloat() }
                    }
                SortType.ASC ->
                    when (_sortModel.category) {
                        SortCategory.NAME -> sortBy { it.symbol }
                        SortCategory.PRICE -> sortBy { it.currentPrice.toFloat() }
                        SortCategory.RATE -> sortBy { it.rate.toFloat() }
                        SortCategory.VOLUME -> sortBy { it.volume.toFloat() }
                    }
            }
        }
    }

    suspend fun clear() {
        _mutex.withLock {
            _list.clear()
        }
    }
}