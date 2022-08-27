package com.example.coinapp.utils

sealed class Resource<out T : Any> {
    class Success<T : Any>(val data: T) : Resource<T>()
    class Error(val message: String?) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}