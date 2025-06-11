package com.ming.domain.model

interface Diffable<T> {
    fun areItemsTheSame(other: T): Boolean
    fun areContentsTheSame(other: T): Boolean
}