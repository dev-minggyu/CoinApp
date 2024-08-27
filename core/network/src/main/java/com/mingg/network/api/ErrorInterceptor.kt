package com.mingg.network.api

import com.mingg.common.InternalServerErrorException
import com.mingg.common.UnknownErrorException
import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.isSuccessful) return response

        throw createException(response.code)
    }
}

private fun createException(httpCode: Int): Exception =
    when (httpCode) {
        500 -> InternalServerErrorException()
        else -> UnknownErrorException()
    }