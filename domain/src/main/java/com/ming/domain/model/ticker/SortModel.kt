package com.ming.domain.model.ticker

data class SortModel(
    var category: SortCategory,
    var type: SortType
)

enum class SortType {
    DESC,
    ASC
}

enum class SortCategory {
    NAME,
    PRICE,
    RATE,
    VOLUME
}