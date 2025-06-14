package com.ming.network.api

import com.ming.common.InternalServerErrorException
import com.ming.common.UnknownErrorException
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