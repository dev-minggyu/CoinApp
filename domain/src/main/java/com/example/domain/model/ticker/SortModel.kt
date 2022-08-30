package com.example.domain.model.ticker

data class SortModel(
    var category: SortCategory,
    var type: SortType
)

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