package com.example.data.model.ticker

import com.example.domain.model.ticker.Ticker
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AtomicTickerList {
    private val _mutex = Mutex()

    private var _list = mutableListOf<Ticker>()

    suspend fun addAll(elements: Collection<Ticker>) {
        _mutex.withLock {
            _list.addAll(elements)
        }
    }

    suspend fun updateTicker(element: Ticker) {
        _mutex.withLock {
            _list.find {
                it.symbol == element.symbol
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

    fun getList(): List<Ticker> = _list.toList()

    suspend fun clear() {
        _mutex.withLock {
            _list.clear()
        }
    }
}