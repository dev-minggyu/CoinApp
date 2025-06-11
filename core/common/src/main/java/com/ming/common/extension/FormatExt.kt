package com.ming.common.extension

import java.text.NumberFormat

fun NumberFormat.formatWithPlusSignPrefix(number: Double): String {
    return when {
        number > 0 -> "+"
        else -> ""
    } + format(number)
}