package com.example.coinapp.enums

import com.example.coinapp.R

enum class SortType {
    NO,
    DESC,
    ASC
}

enum class SortCategory {
    NAME,
    PRICE,
    RATE,
    VOLUME
}

data class SortModel(
    val category: SortCategory,
    val type: SortType
) {
    fun getArrowRes(category: SortCategory): Int {
        return when (this.category) {
            category ->
                when (type) {
                    SortType.NO -> R.drawable.ic_arrow_normal
                    SortType.DESC -> R.drawable.ic_arrow_down
                    SortType.ASC -> R.drawable.ic_arrow_up
                }
            else -> R.drawable.ic_arrow_normal
        }
    }
}