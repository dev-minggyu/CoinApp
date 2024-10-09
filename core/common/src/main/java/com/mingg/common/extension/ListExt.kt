package com.mingg.common.extension

fun <T> MutableList<T>.updateOrInsert(
    predicate: (T) -> Boolean,
    update: T.() -> T,
    insert: T?
) {
    val index = indexOfFirst(predicate)
    if (index != -1) {
        this[index] = this[index].update()
    } else {
        insert?.let { add(it) }
    }
}