package com.ming.domain.utils

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error(val exception: Exception?) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}

sealed class TickerResource<out T : Any> {
    class Update<T : Any>(val data: T) : TickerResource<T>()
    class Refresh<T : Any>(val data: T) : TickerResource<T>()
    class Error(val exception: Exception?) : TickerResource<Nothing>()
}