package com.mingg.common

import com.mingg.domain.utils.Resource

inline fun <T : Any> callApi(transform: () -> T): Resource<T> =
    try {
        Resource.Success(transform.invoke())
    } catch (e: Exception) {
        when (e) {
            is InternalServerErrorException -> Resource.Error(InternalServerErrorException())
            else -> Resource.Error(e)
        }
    }