package com.mingg.coincheck.ui.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mingg.domain.model.Diffable

abstract class BaseListAdapter<T : Diffable<T>, VH : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.areItemsTheSame(newItem)
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.areContentsTheSame(newItem)
        }
    }
) : ListAdapter<T, VH>(diffCallback)