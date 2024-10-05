package com.mingg.coincheck.extension

import android.content.res.TypedArray

inline fun <reified T : Enum<T>> TypedArray.getEnum(index: Int) =
    getInt(
        index,
        -1
    ).let { if (it >= 0) enumValues<T>()[it] else throw IllegalAccessException("No enum found matching index : $index") }