package com.example.coinapp.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.example.coinapp.R
import com.example.coinapp.databinding.ButtonSortBinding
import com.example.coinapp.extension.getEnum
import com.example.domain.model.ticker.SortCategory
import com.example.domain.model.ticker.SortModel
import com.example.domain.model.ticker.SortType

class SortButton(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private lateinit var _binding: ButtonSortBinding

    private lateinit var _sortModel: SortModel

    private var _onSortChangedListener: OnSortChangedListener? = null

    init {
        init(context)
        getAttrs(attrs)
        setListener()
    }

    private fun init(context: Context?) {
        _binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.button_sort,
            this,
            false
        )
        addView(_binding.root)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SortButton)

        val sortCategory = typedArray.getEnum<SortCategory>(R.styleable.SortButton_sortCategory)
        _sortModel = SortModel(sortCategory, SortType.NO)

        _binding.tvSortName.apply {
            text = when (sortCategory) {
                SortCategory.NAME -> context.getString(R.string.sort_coin_name)
                SortCategory.PRICE -> context.getString(R.string.sort_coin_price)
                SortCategory.RATE -> context.getString(R.string.sort_coin_rate)
                SortCategory.VOLUME -> context.getString(R.string.sort_coin_volume)
            }
        }

        typedArray.recycle()
    }

    private fun setListener() {
        setOnClickListener {
            _binding.ivSortArrow.apply {
                _sortModel.type = when (_sortModel.type) {
                    SortType.NO -> SortType.DESC
                    SortType.DESC -> SortType.ASC
                    SortType.ASC -> SortType.NO
                }
                _onSortChangedListener?.onChanged(_sortModel)
            }
        }
    }

    fun setSortState(sortModel: SortModel) {
        _binding.apply {
            if (sortModel.category == _sortModel.category) {
                when (sortModel.type) {
                    SortType.NO -> ivSortArrow.setImageResource(R.drawable.ic_arrow_normal)
                    SortType.DESC -> ivSortArrow.setImageResource(R.drawable.ic_arrow_down)
                    SortType.ASC -> ivSortArrow.setImageResource(R.drawable.ic_arrow_up)
                }
            } else {
                ivSortArrow.setImageResource(R.drawable.ic_arrow_normal)
            }
        }
    }

    fun setOnSortChangedListener(listener: OnSortChangedListener) {
        _onSortChangedListener = listener
    }

    interface OnSortChangedListener {
        fun onChanged(sortModel: SortModel)
    }
}